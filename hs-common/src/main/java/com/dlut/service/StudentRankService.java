package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.StudentRank;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface StudentRankService extends IService<StudentRank> {

    ResponseResult<?> batchUpload(MultipartFile file);

    ResponseResult<?> getStudentRankList(Integer pageNum, Integer pageSize, Long majorId);
}
