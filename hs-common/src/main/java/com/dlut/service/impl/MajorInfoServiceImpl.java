package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.entity.MajorInfo;
import com.dlut.mapper.MajorInfoMapper;
import com.dlut.service.MajorInfoService;
import com.dlut.utils.UserThreadLocalUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MajorInfoServiceImpl extends ServiceImpl<MajorInfoMapper, MajorInfo> implements MajorInfoService {

    private final MajorInfoMapper majorInfoMapper;

    @Override
    public ResponseResult<List<MajorInfo>> getMajorInfoList() {
        // 查询当前书院包含的已分流的专业
        String academyName = UserThreadLocalUtil.getUser().getAcademyName();
        LambdaQueryWrapper<MajorInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MajorInfo::getAcademyName, academyName);
        queryWrapper.eq(MajorInfo::getIsDivert, SystemConstants.DB_DIVERTED);
        List<MajorInfo> majorInfos = majorInfoMapper.selectList(queryWrapper);
        return ResponseResult.okResult(majorInfos);
    }
}
