package com.dlut.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// jwt载荷中自带iat和exp字段，而java类中没有相应字段，jackson转换时会抛unknown异常
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentInfoDto {

    private Long parentId;
    private Long studentNumber;
    private String studentName;
    private String academyName;
    private String majorFullName;
    private String studentClass;
}
