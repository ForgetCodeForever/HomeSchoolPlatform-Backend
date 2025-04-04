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
    public ResponseResult<List<MemorabiliaInfo>> getAcademyMemorabiliaList(String academyName) {
        LambdaQueryWrapper<MemorabiliaInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemorabiliaInfo::getAcademyName, academyName);
        List<MemorabiliaInfo> memorabiliaInfos = memorabiliaInfoMapper.selectList(queryWrapper);
        return ResponseResult.okResult(memorabiliaInfos);
    }
}
