package com.instagenius.postmanagementservice.domain;

import com.instagenius.postmanagementservice.application.*;
import com.instagenius.postmanagementservice.infrastructure.exception.ImageStorageException;
import com.instagenius.postmanagementservice.infrastructure.exception.PostGenerationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostManagementService implements PostManagementUseCase {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;
    private final CoinManagementPort coinManagementPort;
    private final FileStoragePort fileStoragePort;

    public PostManagementService(PostPersistencePort postPersistencePort, PostGenerationPort postGenerationPort, CoinManagementPort coinManagementPort, FileStoragePort fileStoragePort) {
        this.postPersistencePort = postPersistencePort;
        this.postGenerationPort = postGenerationPort;
        this.coinManagementPort = coinManagementPort;
        this.fileStoragePort = fileStoragePort;
    }

    //TODO check how to ensure, that all or operations here are transactional and async
    @Override
    public Post createPost(UUID userId, DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions, String title) {
        GenerationCost generationCost = calculateGenerationCost(descriptionGenerationOptions, imageGenerationOptions);
        System.out.println("Generation cost: " + generationCost.coins());
        UUID operationId = UUID.randomUUID();
        CoinReservation coinReservation = coinManagementPort.reserveCoins(new ReserveCoins(generationCost.coins(), operationId));

        GeneratedDescription generatedDescription;
        GeneratedImage generatedImage;

        try {
            generatedDescription = postGenerationPort.generateDescription(descriptionGenerationOptions);
            generatedImage = postGenerationPort.generateImage(imageGenerationOptions);
        } catch (PostGenerationException exception) {
            coinManagementPort.cancelReservation(new CancelReservation(coinReservation.reservationId()));
            throw exception;
        }

        FileKeyName imageKeyName = new FileKeyName(userId);
        //TODO should wait for image response and then should be async to allow post to be stored in database
        try {
            /* Responses from post generation service are placeholders, so file storage wont actually save images */
            fileStoragePort.uploadFile(imageKeyName, generatedImage);
        } catch(ImageStorageException exception) {
            coinManagementPort.cancelReservation(new CancelReservation(coinReservation.reservationId()));
            throw exception;
        }

        Post post = postPersistencePort.save(new Post(null, userId, title, imageKeyName, generatedImage, generatedDescription, null, null));
        post.setGeneratedImage(generatedImage);

        coinManagementPort.completeReservation(new CompleteReservation(coinReservation.reservationId()));
        return post;
    }

    @Override
    public List<Post> getPostsByUserId(UUID userId) {
        List<Post> posts = postPersistencePort.getPostsByUserId(userId);

        List<FileKeyName> imageKeyNames = posts
                .stream()
                .map(Post::getImageKeyName)
                .toList();

        Map<FileKeyName, String> b64ImagesMap = imageKeyNames
                .parallelStream()//TODO check this in combination with async get of images from file storage
                .collect(Collectors.toMap(
                        keyName -> keyName, keyName -> Base64.getEncoder().encodeToString(fileStoragePort.downloadFile(keyName)))
                );

        for (Post post: posts) {
            FileKeyName imageKeyName = post.getImageKeyName();
            String b64Image = b64ImagesMap.get(imageKeyName);
            post.setGeneratedImage(new GeneratedImage(b64Image));
        }

        return posts;
    }

    @Override
    public Post getPostByUserIdAndId(UUID userId, Long id) {
        Post post = postPersistencePort.getPostByUserIdAndPostId(userId, id);

        String b64Image = Base64.getEncoder().encodeToString(fileStoragePort.downloadFile(post.getImageKeyName()));

        post.setGeneratedImage(new GeneratedImage(b64Image));

        return post;
    }

    //TODO Check the case, when post is deleted from file storage, while error occurs while deleting post from my database.
    //TODO Then, image is deleted from file stroage but post with imagekeyname is not deleted from databse. Check how to solve it.
    @Transactional
    @Override
    public void deletePost(UUID userId, Long id) {
        Post post = postPersistencePort.getPostByUserIdAndPostId(userId, id);

        fileStoragePort.deleteFile(post.getImageKeyName());

        postPersistencePort.deletePostByUserIdAndPostId(userId, id);
    }

    private GenerationCost calculateGenerationCost(DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions) {
        return postGenerationPort.calculateGenerationCost(descriptionGenerationOptions, imageGenerationOptions);
    }
}
