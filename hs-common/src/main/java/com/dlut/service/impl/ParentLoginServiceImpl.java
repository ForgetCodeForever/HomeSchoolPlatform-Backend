package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.entity.ParentInfo;
import com.dlut.mapper.ParentInfoMapper;
import com.dlut.service.ParentLoginService;
import com.dlut.vo.LoginVo;
import org.springframework.stereotype.Service;

@Service
public class ParentLoginServiceImpl extends ServiceImpl<ParentInfoMapper, ParentInfo> implements ParentLoginService {

    @Override
    public ResponseResult<LoginVo> login(ParentInfo parentInfo) {

        return null;
    }
}
