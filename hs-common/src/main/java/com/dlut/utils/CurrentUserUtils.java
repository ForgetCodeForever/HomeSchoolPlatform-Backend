package com.dlut.utils;

import com.dlut.dto.AdminInfoDto;
import com.dlut.dto.ParentInfoDto;
import com.dlut.mapper.AdminUserMapper;
import com.dlut.mapper.ParentInfoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 加入component注解，工具类可以使用spring管理的bean */
@Component
@RequiredArgsConstructor
public class CurrentUserUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AdminUserMapper adminUserMapper;

    /**
     * 使用 Jackson 将 JWT Claims 转换为 AdminInfoDto
     */
    public static AdminInfoDto claimsToUserInfo(Claims claims) {
        if (claims == null) {
            return null;
        }
        return objectMapper.convertValue(claims, AdminInfoDto.class);
    }

    /**
     * 使用 Jackson 将 JWT Claims 转换为 ParentInfoDto
     */
    public static ParentInfoDto claimsToParentInfo(Claims claims) {
        if (claims == null) {
            return null;
        }
        return objectMapper.convertValue(claims, ParentInfoDto.class);
    }

    public Long getCurrentAdminRoleId() {
        AdminInfoDto user = UserThreadLocalUtil.getUser();
        return adminUserMapper.findRoleIdByAdminId(user.getAdminId());
    }
}
