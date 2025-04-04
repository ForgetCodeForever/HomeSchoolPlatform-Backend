package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.RedisConstants;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.AdminInfoDto;
import com.dlut.entity.AdminUser;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.AdminUserMapper;
import com.dlut.service.AdminUserLogoutService;
import com.dlut.utils.UserThreadLocalUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminUserLogoutServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserLogoutService {

    private final StringRedisTemplate stringRedisTemplate;

    public AdminUserLogoutServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ResponseResult<?> logout() {
        // 删除redis中的token信息
        AdminInfoDto user = UserThreadLocalUtil.getUser();
        String adminTokenKey = RedisConstants.LOGIN_TOKEN_ADMIN + user.getAdminId();
        stringRedisTemplate.delete(adminTokenKey);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.LOGOUT.getMsg());
    }

}
