package com.dlut.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
public class StudentInfoExcelDto {

    @ExcelProperty("学号")
    private String studentNumber;

    @ExcelProperty("学生姓名")
    private String studentName;

    @ExcelProperty("书院名称")
    private String academyName;

    @ExcelProperty("专业id")
    private String majorId;

    @ExcelProperty("学生班级")
    private String studentClass;

}
