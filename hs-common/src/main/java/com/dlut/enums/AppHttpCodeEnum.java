package com.dlut.enums;

import lombok.Getter;

@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    SYSTEM_ERROR(500,"系统错误"),
    /**
     * 学校端
     */
    /** 用户登录 */
    USERNAME_NOT_NULL(600, "用户名不能为空"),
    PASSWORD_NOT_NULL(601, "密码不能为空"),
    USER_NOT_EXISTS(602, "用户不存在"),
    PASSWORD_NOT_CORRECT(603, "密码错误"),
    /** 事记管理 */
    MEMORABILIA_TOO_LONG(603, "大事记内容不能多于200字"),
    MEMORABILIA_NOT_EXIST(604, "大事记不存在"),
    /** 修改密码 */
    ORIGIN_PASSWORD_NOT_CORRECT(605, "原密码错误"),
    /** 学生信息管理 */
    EXCEL_EMPTY(606, "Excel内容为空"),
    FILE_FORMAT_INCORRECT(607, "文件格式错误"),
    BATCH_ERROR(608, "上传失败"),
    /**
     * 家长端
     */
    /** 登录 */
    PARENT_NOT_EXISTS(700, "用户不存在");

    final int code;
    final String msg;
    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }
}
