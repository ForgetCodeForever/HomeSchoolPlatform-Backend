package com.dlut.vo;

import com.dlut.dto.AdminInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {

    private String token;
    private AdminInfoDto adminInfoDto;

}
