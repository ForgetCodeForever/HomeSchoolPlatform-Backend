package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo {

    /**
     * 学号
     * 数据库数据类型是bigint，但实体类为String，为了兼容批量导入功能下excel的数据类型
     */
    @TableId
    private String studentNumber;
    
    /** 学生姓名 */
    private String studentName;
    
    /** 书院名称 */
    private String academyName;

    /**
     * 专业id
     * 数据库数据类型是bigint，但实体类为String，为了兼容批量导入功能下excel的数据类型
     */
    private String majorId;
    
    /** 学生班级 */
    private String studentClass;
    
}
