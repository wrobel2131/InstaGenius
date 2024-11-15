package com.instagenius.productmanagementservice.application;


import com.instagenius.productmanagementservice.domain.FileKeyName;
import com.instagenius.productmanagementservice.domain.Image;

public interface FileStoragePort {
    void uploadFile(FileKeyName fileKeyName, Image image);
    byte[] downloadFile(FileKeyName fileKeyName);
    void deleteFile(FileKeyName fileKeyName);
}
