package com.dlut.utils;

import com.dlut.dto.UserInfoDto;

public class UserThreadLocalUtil {

    private final static ThreadLocal<UserInfoDto> USER_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程
    public static void setUser(UserInfoDto userInfoDto) {
        USER_THREAD_LOCAL.set(userInfoDto);
    }

    // 从线程中获取
    public static UserInfoDto getUser() {
        return USER_THREAD_LOCAL.get();
    }

    // 清理
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

}
