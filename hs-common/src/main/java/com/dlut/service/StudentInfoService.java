package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.StudentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface StudentInfoService extends IService<StudentInfo> {

    ResponseResult<?> batchUpload(MultipartFile file);

    ResponseResult<?> getStudentInfoList(Integer pageNum, Integer pageSize);
}
