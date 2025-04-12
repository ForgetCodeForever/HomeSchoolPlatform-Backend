package com.dlut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserListVo {

    private Long adminId;
    private String username;
    private String adminName;
    private String roleName;
    private String academyName;

}
