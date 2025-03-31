package com.dlut.utils;

import com.dlut.dto.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

public class JwtUserConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 使用 Jackson 将 JWT Claims 转换为 UserInfoDto
     */
    public static UserInfoDto claimsToUserInfo(Claims claims) {
        if (claims == null) {
            return null;
        }
        return objectMapper.convertValue(claims, UserInfoDto.class);
    }
}
