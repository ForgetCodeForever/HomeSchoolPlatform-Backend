//package com.dlut.service.impl;
//
//import com.dlut.config.MinIOConfig;
//import com.dlut.service.MinioService;
//import io.minio.*;
//import io.minio.http.Method;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Import;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//@Service
//@Import(MinIOConfig.class)
//@AllArgsConstructor
//public class MinioServiceImpl implements MinioService {
//
//    private final MinioClient minioClient;
//    private final MinIOConfig minIOConfig;
//
//    @Override
//    public String generateFilePath(MultipartFile file) throws Exception {
//        String originalName = file.getOriginalFilename();
//        String extension = ""; // 拓展名
//
//        if (originalName != null && originalName.contains(".")) {
//            extension = originalName.substring(originalName.lastIndexOf("."));
//        }
//
//        // 日期路径
//        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//        // UUID 文件名
//        String randomName = UUID.randomUUID().toString().replaceAll("-", "");
//        String objectName = datePath + "/" + randomName + extension;
//
//        InputStream inputStream = file.getInputStream();
//
//        uploadFile(objectName, inputStream, file.getSize(), file.getContentType());
//
//        return objectName;
//    }
//
//    @Override
//    public void uploadFile(String fileName, InputStream stream, long size, String contentType) throws Exception {
//        String objectName = generateFilePath(fileName);
//        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIOConfig.getBucket()).build());
//        if (!exists) {
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIOConfig.getBucket()).build());
//        }
//
//        minioClient.putObject(
//                PutObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .stream(stream, size, -1)
//                        .contentType(contentType)
//                        .build()
//        );
//    }
//
//    @Override
//    public InputStream downloadFile(String objectName) throws Exception {
//        return minioClient.getObject(
//                GetObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .build()
//        );
//    }
//
//    @Override
//    public String getFileUrl(String objectName, int expires) throws Exception {
//        return minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .method(Method.GET)
//                        .expiry(expires, TimeUnit.SECONDS)
//                        .build()
//        );
//    }
//
//    @Override
//    public void deleteFile(String objectName) throws Exception {
//        minioClient.removeObject(
//                RemoveObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .build()
//        );
//    }
//}
