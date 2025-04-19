package com.dlut.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    String generateFilePath(MultipartFile file) throws Exception;

    void uploadFile(String objectName, InputStream stream, long size, String contentType) throws Exception;

    InputStream downloadFile(String objectName) throws Exception;

    String getFileUrl(String objectName, int expires) throws Exception;

    void deleteFile(String objectName) throws Exception;
}
