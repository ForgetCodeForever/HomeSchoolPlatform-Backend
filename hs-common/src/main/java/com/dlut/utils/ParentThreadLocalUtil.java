package com.dlut.utils;

import com.dlut.dto.ParentInfoDto;

public class ParentThreadLocalUtil {

    private final static ThreadLocal<ParentInfoDto> PARENT_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程
    public static void setUser(ParentInfoDto parentInfoDto) {
        PARENT_THREAD_LOCAL.set(parentInfoDto);
    }

    // 从线程中获取
    public static ParentInfoDto getUser() {
        return PARENT_THREAD_LOCAL.get();
    }

    // 清理
    public static void removeUser() {
        PARENT_THREAD_LOCAL.remove();
    }
}
