package com.dlut.enums;

import lombok.Getter;

@Getter
public enum SuccessHttpMessageEnum {
    LOGIN("登录成功"),
    LOGOUT("退出成功"),
    REGISTER("注册成功"),
    BATCH("导入成功"),
    ADD("添加成功"),
    DEL("删除成功"),
    EDIT("编辑成功"),
    QUERY("查询成功");

    final String msg;

    SuccessHttpMessageEnum(String successMessage){
        this.msg = successMessage;
    }
}
