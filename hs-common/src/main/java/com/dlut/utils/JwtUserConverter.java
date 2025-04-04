package com.dlut.utils;

import com.dlut.dto.AdminInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

public class JwtUserConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 使用 Jackson 将 JWT Claims 转换为 AdminInfoDto
     */
    public static AdminInfoDto claimsToUserInfo(Claims claims) {
        if (claims == null) {
            return null;
        }
        return objectMapper.convertValue(claims, AdminInfoDto.class);
    }
}
