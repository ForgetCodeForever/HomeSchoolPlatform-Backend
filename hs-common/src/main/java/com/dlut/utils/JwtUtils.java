package com.dlut.utils;

import com.dlut.constants.RedisConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "Z26EfbX73c2f2efye3i6cD68oa3O2cIfdJ34343sb6Ncfw3c"; // 需要替换为安全的密钥
    private static final long EXPIRATION_TIME = 1000 * 60 * RedisConstants.LOGIN_TOKEN_TTL; // 1小时

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * 生成JWT令牌
     * @param claims 自定义负载
     * @return JWT字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param token JWT字符串
     * @return 解析后的Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 校验JWT令牌是否有效
     * @param token JWT字符串
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "dayu");
        claims.put("role", "大煜书院负责人");

        String token = generateToken(claims);
        System.out.println("Generated Token: " + token);

        Claims parsedClaims = parseToken(token);
        System.out.println("Parsed Claims: " + parsedClaims);

        boolean isValid = validateToken(token);
        System.out.println("Is Token Valid: " + isValid);
    }

}