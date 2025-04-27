package com.instagenius.productmanagementservice.infrastructure.adapter;

import com.instagenius.productmanagementservice.application.FileStoragePort;
import com.instagenius.productmanagementservice.domain.FileKeyName;
import com.instagenius.productmanagementservice.domain.Image;
import com.instagenius.productmanagementservice.infrastructure.exception.ImageStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class S3FileStorageAdapter implements FileStoragePort {
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public void uploadFile(FileKeyName fileKeyName, Image image) {
        log.debug("Uploading file {} to {}", fileKeyName, bucketName); //TODO change it to working upload, if the whole creating post flow works
        PutObjectRequest putObjectRequest = PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key(fileKeyName.keyName())
                .build();
        byte[] imageInBytes = Base64.getDecoder().decode(image.b64Image());

        try (InputStream inputStream = new ByteArrayInputStream(imageInBytes)) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, imageInBytes.length));
        } catch (IOException | S3Exception e) {
            log.debug(e.getMessage());
            throw new ImageStorageException("Failed to upload the file!");
        }
    }

    @Override
    public byte[] downloadFile(FileKeyName fileKeyName) {
        log.debug("Downloading file {} from {}", fileKeyName, bucketName);
//        return new byte[10];
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key(fileKeyName.keyName())
                .build();
        try(InputStream inputStream = s3Client.getObject(getObjectRequest)) {
            return inputStream.readAllBytes();
        } catch (IOException | S3Exception e) {
            log.debug(e.getMessage());
            throw new ImageStorageException("Failed to download the file!");
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
            log.debug(e.getMessage());
            throw new ImageStorageException("Failed to delete the file!");
        }
    }
}
