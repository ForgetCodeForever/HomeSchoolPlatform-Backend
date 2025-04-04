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
public class AdminInfoDto {

    private Long adminId;      // 用户id
    private String adminName;  // 用户名
    private String roleName;  // 角色名
    private String academyName; // 书院名

}
