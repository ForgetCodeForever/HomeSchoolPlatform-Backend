package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.entity.ParentInfo;
import com.dlut.mapper.ParentInfoMapper;
import com.dlut.service.ParentInfoService;
import org.springframework.stereotype.Service;

@Service
public class ParentInfoServiceImpl extends ServiceImpl<ParentInfoMapper, ParentInfo> implements ParentInfoService {
    
}
