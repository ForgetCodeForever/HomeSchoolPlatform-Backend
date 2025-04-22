package com.dlut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.ResponseResult;
import com.dlut.entity.ParentInfo;
import com.dlut.vo.ParentLoginVo;

public interface ParentLoginService extends IService<ParentInfo> {

    ResponseResult<ParentLoginVo> login(ParentInfo parentInfo);
}
