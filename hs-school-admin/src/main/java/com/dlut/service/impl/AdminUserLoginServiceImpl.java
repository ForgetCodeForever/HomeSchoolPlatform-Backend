package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.RedisConstants;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.UserInfoDto;
import com.dlut.entity.AdminUser;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.exception.SystemException;
import com.dlut.mapper.AdminUserMapper;
import com.dlut.service.AdminUserLoginService;
import com.dlut.utils.JwtUtils;
import com.dlut.utils.PasswordEncryptor;
import com.dlut.vo.LoginVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AdminUserLoginServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserLoginService {

    private final AdminUserMapper adminUserMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public AdminUserLoginServiceImpl(AdminUserMapper adminUserMapper, StringRedisTemplate stringRedisTemplate) {
        this.adminUserMapper = adminUserMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ResponseResult<LoginVo> login(AdminUser adminUser) {
        // 1.对数据进行非空判断
        if (!StringUtils.hasText(adminUser.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(adminUser.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        // 查询数据库中的用户信息
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getUsername, adminUser.getUsername());
        AdminUser dbUser = adminUserMapper.selectOne(queryWrapper);
        if (dbUser == null) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXISTS);
        }

        // 2.判断用户输入密码是否正确
        String inputPassword = adminUser.getPassword();
        String dbPassword = dbUser.getPassword();
        if (!PasswordEncryptor.verify(inputPassword, dbPassword)) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_CORRECT);
        }

        // 3.生成JWT令牌（token）
        String roleName = adminUserMapper.findRoleNamesByUsername(dbUser.getUsername());
        Map<String, Object> claims = new HashMap<>();
        Long adminId = dbUser.getAdminId();
        String adminName = dbUser.getAdminName();
        claims.put(SystemConstants.USER_ID, adminId.toString());
        claims.put(SystemConstants.USER_NAME, adminName);
        claims.put(SystemConstants.ROLE_NAME, roleName);

        String token = JwtUtils.generateToken(claims);

        // 4.将token存入redis，并设置过期时间
        /** 改Bug：adminUser是用户输入的信息，没有adminId */
        String adminTokenKey = RedisConstants.LOGIN_TOKEN_ADMIN + dbUser.getAdminId();
        stringRedisTemplate.opsForValue().set(adminTokenKey, token);
        stringRedisTemplate.expire(adminTokenKey, RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);

        // 5.封装数据
        UserInfoDto userInfoDto = new UserInfoDto(adminId, adminName, roleName);
        LoginVo loginVo = new LoginVo(token, userInfoDto);

        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.LOGIN.getMsg(), loginVo);
    }

}
