package com.dlut.utils;

import com.dlut.dto.AdminInfoDto;

public class UserThreadLocalUtil {

    private final static ThreadLocal<AdminInfoDto> USER_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程
    public static void setUser(AdminInfoDto adminInfoDto) {
        USER_THREAD_LOCAL.set(adminInfoDto);
    }

    // 从线程中获取
    public static AdminInfoDto getUser() {
        return USER_THREAD_LOCAL.get();
    }

    // 清理
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

}
