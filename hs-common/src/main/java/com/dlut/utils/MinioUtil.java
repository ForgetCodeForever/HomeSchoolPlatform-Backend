package com.dlut.utils;

import com.dlut.config.MinIOConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Import(MinIOConfig.class)
@AllArgsConstructor
public class MinioUtil {

    private final MinioClient minioClient;
    private final MinIOConfig minIOConfig;

    /**
     * 上传文件并返回完整URL路径
     */
    public String upload(MultipartFile file) throws Exception {
        String objectName = buildObjectName(file.getOriginalFilename());
        uploadFile(objectName, file.getInputStream(), file.getSize(), file.getContentType());
        return getObjectUrl(objectName);
    }

    /**
     * 构建存储在 MinIO 的对象名（带日期路径）
     */
    public String buildObjectName(String originalFileName) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return datePath + "/" + uuid + extension;
    }

    /**
     * 上传文件到 MinIO
     */
    public void uploadFile(String objectName, InputStream stream, long size, String contentType) throws Exception {
        String bucketName = minIOConfig.getBucket();

        // 如果桶不存在，创建桶
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // 上传文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(stream, size, -1)
                        .contentType(contentType)
                        .build()
        );
    }

    /**
     * 下载文件，返回 InputStream
     */
    public InputStream downloadFile(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minIOConfig.getBucket())
                        .object(objectName)
                        .build()
        );
    }

    /**
     * 获取文件的预签名访问URL（临时，有过期时间）
     */
    public String getPresignedUrl(String objectName, int expiresSeconds) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(minIOConfig.getBucket())
                        .object(objectName)
                        .method(Method.GET)
                        .expiry(expiresSeconds, TimeUnit.SECONDS)
                        .build()
        );
    }

    /**
     * 获取文件的静态URL（直接拼接，桶需要设置 public 访问）
     */
    public String getObjectUrl(String objectName) {
        return minIOConfig.getEndpoint() + "/" + minIOConfig.getBucket() + "/" + objectName;
    }

    /**
     * 删除文件
     */
    public void deleteFile(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minIOConfig.getBucket())
                        .object(objectName)
                        .build()
        );
    }
}
