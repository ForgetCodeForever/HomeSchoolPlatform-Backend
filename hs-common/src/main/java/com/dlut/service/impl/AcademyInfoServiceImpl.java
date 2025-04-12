package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.entity.AcademyInfo;
import com.dlut.mapper.AcademyInfoMapper;
import com.dlut.service.AcademyInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademyInfoServiceImpl extends ServiceImpl<AcademyInfoMapper, AcademyInfo> implements AcademyInfoService {

    @Override
    public ResponseResult<List<AcademyInfo>> getAcademyList() {
        List<AcademyInfo> list = list();
        return ResponseResult.okResult(list);
    }
}
