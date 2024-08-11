package com.instagenius.postmanagementservice.infrastructure.adapters;

import com.instagenius.postmanagementservice.application.FileStoragePort;
import com.instagenius.postmanagementservice.domain.FileKeyName;
import com.instagenius.postmanagementservice.domain.GeneratedImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class S3FileStorageAdapter implements FileStoragePort {
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public void uploadFile(FileKeyName fileKeyName, GeneratedImage generatedImage) {
        PutObjectRequest putObjectRequest = PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key(fileKeyName.keyName())
                .build();
        byte[] image = Base64.getDecoder().decode(generatedImage.b64Image());

        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.length));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload the file", e); //TODO create my own exception
        }
    }

    @Override
    public byte[] downloadFile(FileKeyName fileKeyName) {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key(fileKeyName.keyName())
                .build();
        try(InputStream inputStream = s3Client.getObject(getObjectRequest)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download the file", e); //TODO create my own exception

        } catch (S3Exception e) {
            throw new RuntimeException("Error with S3 client: " + e.getLocalizedMessage()); //TODO create my own exception
        }
    }

    @Override
    public void deleteFile(FileKeyName fileKeyName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                .builder()
                .bucket(bucketName)
                .key(fileKeyName.keyName())
                .build();
        try {
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Error with S3 client: " + e.getLocalizedMessage()); //TODO create my own exception
        }
    }
}
