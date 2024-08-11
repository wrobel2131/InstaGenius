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

@RequiredArgsConstructor
public class PostManagementService implements PostManagementUseCase {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;
    private final FileStoragePort fileStoragePort;

    @Override
    public Post createPost(UUID userId, DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions, String title) {
        GeneratedDescription generatedDescription = postGenerationPort.generateDescription(descriptionGenerationOptions);

        GeneratedImage generatedImage = postGenerationPort.generateImage(imageGenerationOptions);

        FileKeyName imageKeyName = new FileKeyName(userId);
        fileStoragePort.uploadFile(imageKeyName, generatedImage);

        Post post = postPersistencePort.save(new Post(null, userId, title, imageKeyName, generatedImage, generatedDescription, null, null));
        post.setGeneratedImage(generatedImage);

        return post;
    }

    @Override
    public List<Post> getPosts(UUID userId) {
        List<Post> posts = postPersistencePort.getPostsByUserId(userId);

        List<FileKeyName> imageKeyNames = posts
                .stream()
                .map(Post::getImageKeyName)
                .toList();

        Map<FileKeyName, String> b64ImagesMap = imageKeyNames
                .parallelStream()
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
    public Post getPostById(UUID userId, Long id) {
        Post post = postPersistencePort.getPostByUserIdAndPostId(userId, id);

        String b64Image = Base64.getEncoder().encodeToString(fileStoragePort.downloadFile(post.getImageKeyName()));

        post.setGeneratedImage(new GeneratedImage(b64Image));

        return post;
    }

//    @Override
//    public Post updatePost() {
//        return null;
//    }

    @Transactional
    @Override
    public void deletePost(UUID userId, Long id) {
        Post post = postPersistencePort.getPostByUserIdAndPostId(userId, id);

        fileStoragePort.deleteFile(post.getImageKeyName());

        postPersistencePort.deletePostByUserIdAndPostId(userId, id);
    }
}
