package com.nova.team.utp_connect_backend.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeImage(MultipartFile file, String folderName);

    void deleteImage(String fileName, String folderName);

}
