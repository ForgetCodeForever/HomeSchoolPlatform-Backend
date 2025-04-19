package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.MajorData;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MajorDataService extends IService<MajorData> {

    ResponseResult<?> addMajorData(MajorData majorData);

    ResponseResult<?> getMajorDataList(Long majorId);

    ResponseResult<?> editMajorData(MajorData majorData);

    ResponseResult<?> getMajorData(Long dataId);

    ResponseResult<?> deleteMajorData(Long dataId);
}
