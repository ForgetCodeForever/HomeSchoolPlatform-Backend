package com.dlut;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.dlut.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public ResponseResult(Integer code, String msg) {
        this(code, msg, null);
    }

    public ResponseResult(Integer code, T data) {
        this(code, AppHttpCodeEnum.SUCCESS.getMsg(), data);
    }

    // 通用的成功响应
    public static <T> ResponseResult<T> okResult() {
        return new ResponseResult<>(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseResult<T> okResult(T data) {
        return new ResponseResult<>(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseResult<T> okResult(Integer code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> okResult(Integer code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

    // 通用的错误响应
    public static <T> ResponseResult<T> errorResult(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums) {
        return errorResult(enums.getCode(), enums.getMsg());
    }

    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums, String msg) {
        return errorResult(enums.getCode(), msg);
    }
}
