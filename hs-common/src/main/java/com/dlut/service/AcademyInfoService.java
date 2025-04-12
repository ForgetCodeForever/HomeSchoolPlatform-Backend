package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.AcademyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AcademyInfoService extends IService<AcademyInfo> {

    ResponseResult<List<AcademyInfo>> getAcademyList();
}
