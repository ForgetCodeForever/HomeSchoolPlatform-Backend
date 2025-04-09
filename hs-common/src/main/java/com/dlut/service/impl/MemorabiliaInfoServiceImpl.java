package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.entity.MemorabiliaInfo;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.exception.SystemException;
import com.dlut.mapper.MemorabiliaInfoMapper;
import com.dlut.service.MemorabiliaInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemorabiliaInfoServiceImpl extends ServiceImpl<MemorabiliaInfoMapper, MemorabiliaInfo> implements MemorabiliaInfoService {

    private final MemorabiliaInfoMapper memorabiliaInfoMapper;

    public MemorabiliaInfoServiceImpl(MemorabiliaInfoMapper memorabiliaInfoMapper) {
        this.memorabiliaInfoMapper = memorabiliaInfoMapper;
    }

    @Override
    public ResponseResult<?> addMemorabilia(MemorabiliaInfo memorabiliaInfo) {
        // 大事记内容不能多于200字
        if (memorabiliaInfo.getMemorabiliaContent().length() > 200) {
            throw new SystemException(AppHttpCodeEnum.MEMORABILIA_TOO_LONG);
        }
        save(memorabiliaInfo);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.ADD.getMsg());
    }

    @Override
    public ResponseResult<List<MemorabiliaInfo>> getAcademyMemorabiliaList(String academyName, Integer year, Integer month, String keyword) {
        LambdaQueryWrapper<MemorabiliaInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemorabiliaInfo::getAcademyName, academyName);
        /**
        Bug：会查出搜索月下个月第一天的信息
        if (year != null && month != null) {
            // 构造一个月的时间范围
            LocalDateTime startTime = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime endTime = startTime.plusMonths(1);
            queryWrapper.between(MemorabiliaInfo::getMemorabiliaTime, startTime, endTime);
        }
         */
        if (year != null && month != null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            queryWrapper.between(MemorabiliaInfo::getMemorabiliaTime, startDate, endDate);
        }

        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(MemorabiliaInfo::getMemorabiliaContent, keyword);
        }

        queryWrapper.orderByDesc(MemorabiliaInfo::getMemorabiliaTime);
        List<MemorabiliaInfo> list = memorabiliaInfoMapper.selectList(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult<?> editMemorabilia(MemorabiliaInfo memorabiliaInfo) {
        if(!memorabiliaExist(memorabiliaInfo.getMemorabiliaId())) {
            throw new SystemException(AppHttpCodeEnum.MEMORABILIA_NOT_EXIST);
        }
        memorabiliaInfoMapper.updateById(memorabiliaInfo);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.EDIT.getMsg());
    }

    public boolean memorabiliaExist(Long memorabiliaId) {
        LambdaQueryWrapper<MemorabiliaInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemorabiliaInfo::getMemorabiliaId, memorabiliaId);
        return  count(queryWrapper) > 0;
    }

    @Override
    public ResponseResult<MemorabiliaInfo> getMemorabilia(Long memorabiliaId) {
        MemorabiliaInfo memorabiliaInfo = memorabiliaInfoMapper.selectById(memorabiliaId);
        return ResponseResult.okResult(memorabiliaInfo);
    }

    @Override
    public ResponseResult<?> deleteMemorabilia(Long memorabiliaId) {
        memorabiliaInfoMapper.deleteById(memorabiliaId);
            return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.DEL.getMsg());
    }

}
