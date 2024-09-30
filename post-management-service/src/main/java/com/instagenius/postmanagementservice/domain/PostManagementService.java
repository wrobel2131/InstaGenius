package com.instagenius.postmanagementservice.domain;

import com.instagenius.postmanagementservice.application.FileStoragePort;
import com.instagenius.postmanagementservice.application.PostGenerationPort;
import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.application.PostPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostManagementService implements PostManagementUseCase {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;
    private final FileStoragePort fileStoragePort;

    public PostManagementService(PostPersistencePort postPersistencePort, PostGenerationPort postGenerationPort, FileStoragePort fileStoragePort) {
        this.postPersistencePort = postPersistencePort;
        this.postGenerationPort = postGenerationPort;
        this.fileStoragePort = fileStoragePort;
    }

    //TODO check how to ensure, that all or operations here are transactional
    @Override
    public Post createPost(UUID userId, DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions, String title) {
        //TODO should be async
        GeneratedDescription generatedDescription = postGenerationPort.generateDescription(descriptionGenerationOptions);

        //TODO should be async
        GeneratedImage generatedImage = postGenerationPort.generateImage(imageGenerationOptions);

        FileKeyName imageKeyName = new FileKeyName(userId);
        //TODO should wait for image response and then should be async to allow post to be stored in database
        fileStoragePort.uploadFile(imageKeyName, generatedImage);

        Post post = postPersistencePort.save(new Post(null, userId, title, imageKeyName, generatedImage, generatedDescription, null, null));
        post.setGeneratedImage(generatedImage);

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

//    @Override
//    public Post updatePost() {
//        return null;
//    }

    //TODO Check the case, when post is deleted from file storage, while error occurs while deleting post from my database.
    //TODO Then, image is deleted from file stroage but post with imagekeyname is not deleted from databse. Check how to solve it.
    @Transactional
    @Override
    public void deletePost(UUID userId, Long id) {
        Post post = postPersistencePort.getPostByUserIdAndPostId(userId, id);

        fileStoragePort.deleteFile(post.getImageKeyName());

        postPersistencePort.deletePostByUserIdAndPostId(userId, id);
    }
}
