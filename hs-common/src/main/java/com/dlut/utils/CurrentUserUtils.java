package com.dlut.utils;

import com.dlut.dto.AdminInfoDto;
import com.dlut.mapper.AdminUserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

/** 加入component注解，工具类可以使用spring管理的bean */
@Component
public class CurrentUserUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AdminUserMapper adminUserMapper;

    public CurrentUserUtils(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }

    /**
     * 使用 Jackson 将 JWT Claims 转换为 AdminInfoDto
     */
    public static AdminInfoDto claimsToUserInfo(Claims claims) {
        if (claims == null) {
            return null;
        }
        return objectMapper.convertValue(claims, AdminInfoDto.class);
    }

    public Long getCurrentAdminRoleId() {
        AdminInfoDto user = UserThreadLocalUtil.getUser();
        return adminUserMapper.findRoleIdByAdminId(user.getAdminId());
    }
}
