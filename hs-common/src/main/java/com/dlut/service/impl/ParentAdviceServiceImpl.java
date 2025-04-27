package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.entity.ParentAdvice;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.ParentAdviceMapper;
import com.dlut.service.ParentAdviceService;
import com.dlut.utils.ParentThreadLocalUtil;
import com.dlut.utils.UserThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentAdviceServiceImpl extends ServiceImpl<ParentAdviceMapper, ParentAdvice> implements ParentAdviceService {

    private final ParentAdviceMapper parentAdviceMapper;

    @Override
    public ResponseResult<?> addAdvice(ParentAdvice parentAdvice) {
        Long parentId = ParentThreadLocalUtil.getUser().getParentId();
        String adviceTitle = parentAdvice.getAdviceTitle();
        String adviceContent = parentAdvice.getAdviceContent();
        LocalDateTime adviceTime = LocalDateTime.now();
        for (Long l : parentAdvice.getAdminIdList()) {
            ParentAdvice advice = new ParentAdvice();
            advice.setParentId(parentId);
            advice.setAdviceTitle(adviceTitle);
            advice.setAdviceContent(adviceContent);
            advice.setAdminId(l);
            advice.setAdviceTime(adviceTime);
            save(advice);
        }
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.ADVISE.getMsg());
    }

    @Override
    public ResponseResult<?> getAdviceList() {
        // 获取当前管理员、状态为0或1的建议
        Long adminId = UserThreadLocalUtil.getUser().getAdminId();
        LambdaQueryWrapper<ParentAdvice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParentAdvice::getAdminId, adminId);
        queryWrapper.in(ParentAdvice::getStatus, Arrays.asList(SystemConstants.DB_PENDING, SystemConstants.DB_HANDLED));
        queryWrapper.orderByDesc(ParentAdvice::getAdviceTime);
        List<ParentAdvice> parentAdvices = parentAdviceMapper.selectList(queryWrapper);
        return ResponseResult.okResult(parentAdvices);
    }

    @Override
    public ResponseResult<?> replyAdvice(ParentAdvice parentAdvice) {
        ParentAdvice dbAdvice = parentAdviceMapper.selectById(parentAdvice.getAdviceId());
        dbAdvice.setReplyContent(parentAdvice.getReplyContent());
        dbAdvice.setReplyTime(LocalDateTime.now());
        dbAdvice.setStatus(SystemConstants.DB_HANDLED);
        parentAdviceMapper.updateById(dbAdvice);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.REPLY.getMsg());
    }
}
