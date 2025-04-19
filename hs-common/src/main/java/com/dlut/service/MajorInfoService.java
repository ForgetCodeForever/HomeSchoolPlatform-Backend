package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.MajorInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MajorInfoService extends IService<MajorInfo> {

    ResponseResult<List<MajorInfo>> getMajorInfoList();
}
