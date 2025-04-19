package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.entity.MajorData;
import com.dlut.entity.MajorInfo;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.MajorDataMapper;
import com.dlut.service.MajorDataService;
import com.dlut.service.MajorInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MajorDataServiceImpl extends ServiceImpl<MajorDataMapper, MajorData> implements MajorDataService {

    private final MajorDataMapper majorDataMapper;
    private final MajorInfoService majorInfoService;

    @Override
    public ResponseResult<?> addMajorData(MajorData majorData) {
        save(majorData);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.ADD.getMsg());
    }

    @Override
    public ResponseResult<?> getMajorDataList(Long majorId) {
        LambdaQueryWrapper<MajorData> queryWrapper = new LambdaQueryWrapper<>();
        if (majorId != null) {
            queryWrapper.eq(MajorData::getMajorId, majorId);
            queryWrapper.orderByAsc(MajorData::getMajorId);
            queryWrapper.orderByDesc(MajorData::getYear);
            List<MajorData> majorData = majorDataMapper.selectList(queryWrapper);
            return ResponseResult.okResult(majorData);
        }
        List<MajorInfo> majorList = majorInfoService.getMajorInfoList().getData();
        List<Long> majorIds = majorList.stream()
                .map(MajorInfo::getMajorId)
                .collect(Collectors.toList());
        queryWrapper.in(MajorData::getMajorId, majorIds);
        queryWrapper.orderByAsc(MajorData::getMajorId);
        queryWrapper.orderByDesc(MajorData::getYear);
        List<MajorData> majorData = majorDataMapper.selectList(queryWrapper);
        return ResponseResult.okResult(majorData);
    }

    @Override
    public ResponseResult<?> editMajorData(MajorData majorData) {
        majorDataMapper.updateById(majorData);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.EDIT.getMsg());
    }

    @Override
    public ResponseResult<?> getMajorData(Long dataId) {
        MajorData majorData = majorDataMapper.selectById(dataId);
        return ResponseResult.okResult(majorData);
    }

    @Override
    public ResponseResult<?> deleteMajorData(Long dataId) {
        majorDataMapper.deleteById(dataId);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.DEL.getMsg());
    }
}
