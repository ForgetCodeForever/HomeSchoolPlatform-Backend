package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.exception.SystemException;
import com.dlut.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MinioUploadController {

    private final MinioUtil minioUtil;

    @PostMapping("/uploadImage")
    public ResponseResult<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = minioUtil.upload(file);
            Map<String, Object> response = new HashMap<>();
            response.put("url", url);
            return ResponseResult.okResult(response);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MINIO_UPLOAD_ERROR);
        }
    }
}
