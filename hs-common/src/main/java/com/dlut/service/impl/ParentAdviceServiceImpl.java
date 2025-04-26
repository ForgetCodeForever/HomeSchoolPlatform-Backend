package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.entity.ParentAdvice;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.ParentAdviceMapper;
import com.dlut.service.ParentAdviceService;
import com.dlut.utils.ParentThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ParentAdviceServiceImpl extends ServiceImpl<ParentAdviceMapper, ParentAdvice> implements ParentAdviceService {

    @Override
    public ResponseResult<?> addAdvice(ParentAdvice parentAdvice) {
        Long parentId = ParentThreadLocalUtil.getUser().getParentId();
        String adviceContent = parentAdvice.getAdviceContent();
        LocalDateTime adviceTime = LocalDateTime.now();
        for (Long l : parentAdvice.getAdminIdList()) {
            ParentAdvice advice = new ParentAdvice();
            advice.setParentId(parentId);
            advice.setAdviceContent(adviceContent);
            advice.setAdminId(l);
            advice.setAdviceTime(adviceTime);
            save(advice);
        }
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.ADVISE.getMsg());
    }
}
