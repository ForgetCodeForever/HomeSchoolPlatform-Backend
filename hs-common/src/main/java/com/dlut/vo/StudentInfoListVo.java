package com.dlut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoListVo {

    private String studentNumber;

    private String studentName;

    private String academyName;

    private String majorFullName;

    private String isDivert;

    private String studentClass;

}
