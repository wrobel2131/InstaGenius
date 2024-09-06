package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.FileKeyName;
import com.instagenius.postmanagementservice.domain.GeneratedImage;

public interface FileStoragePort {
    void uploadFile(FileKeyName fileKeyName, GeneratedImage generatedImage);
    byte[] downloadFile(FileKeyName fileKeyName);
    void deleteFile(FileKeyName fileKeyName);
}
