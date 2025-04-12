package com.dlut.interceptor;

import com.dlut.constants.RedisConstants;
import com.dlut.dto.AdminInfoDto;
import com.dlut.utils.CurrentUserUtils;
import com.dlut.utils.JwtUtils;
import com.dlut.utils.UserThreadLocalUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return true; // 放行
        }
        String token = authorizationHeader.substring(7);
        Claims claims = JwtUtils.parseToken(token);

        // 2.解析用户信息，存入ThreadLocal
        AdminInfoDto adminInfoDto = CurrentUserUtils.claimsToUserInfo(claims);
        UserThreadLocalUtil.setUser(adminInfoDto);

        // 3.如果redis中的token剩余时间小于5分钟，则生成新的token
        Long adminId = adminInfoDto.getAdminId();
        String redisKey = RedisConstants.LOGIN_TOKEN_ADMIN + adminId;

        Long expireTime = stringRedisTemplate.getExpire(redisKey, TimeUnit.MINUTES);
        if (expireTime == null || expireTime <= 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 4.如果token剩余时间小于5分钟，则生成新的token
        if (expireTime < RedisConstants.TOKEN_REFRESH_THRESHOLD) {
            String newToken = JwtUtils.generateToken(claims);
            // 更新 Redis
            stringRedisTemplate.opsForValue().set(redisKey, newToken, RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);
            // 在响应头中返回新 Token
            response.setHeader("NewToken", "Bearer " + newToken);
        }
        return true; // 继续执行后续请求
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserThreadLocalUtil.removeUser();
    }

}
