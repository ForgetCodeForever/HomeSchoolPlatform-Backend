package com.dlut.enums;

import lombok.Getter;

@Getter
public enum SuccessHttpMessageEnum {
    LOGIN("登录成功"),
    LOGOUT("退出成功");

    final String msg;

    SuccessHttpMessageEnum(String successMessage){
        this.msg = successMessage;
    }
}
