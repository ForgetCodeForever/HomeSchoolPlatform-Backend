package com.dlut.interceptor;

import com.dlut.constants.RedisConstants;
import com.dlut.dto.ParentInfoDto;
import com.dlut.utils.CurrentUserUtils;
import com.dlut.utils.JwtUtils;
import com.dlut.utils.ParentThreadLocalUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // 放行
        }
        String token = authorizationHeader.substring(7);
        Claims claims = JwtUtils.parseToken(token);

        // 2.解析用户信息，存入ThreadLocal
        ParentInfoDto parentInfoDto = CurrentUserUtils.claimsToParentInfo(claims);
        ParentThreadLocalUtil.setUser(parentInfoDto);

        // 3.如果redis中的token过期，则拦截
        Long parentId = parentInfoDto.getParentId();
        String redisKey = RedisConstants.LOGIN_TOKEN_PARENT + parentId;

        Long expireTime = stringRedisTemplate.getExpire(redisKey, TimeUnit.MINUTES);
        if (expireTime == null || expireTime <= 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 放行
        return true; // 继续执行后续请求
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        ParentThreadLocalUtil.removeUser();
    }
}
