package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.ParentAdvice;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ParentAdviceService extends IService<ParentAdvice> {

    ResponseResult<?> addAdvice(ParentAdvice parentAdvice);

    ResponseResult<?> getAdviceList();

    ResponseResult<?> replyAdvice(ParentAdvice parentAdvice);

    ResponseResult<?> getParentAdviceList();
}
