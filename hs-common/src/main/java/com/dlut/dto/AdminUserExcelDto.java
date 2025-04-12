package com.dlut.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AdminUserExcelDto {

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("管理员名")
    private String adminName;

    @ExcelProperty("书院ID")
    private Long academyId;

}