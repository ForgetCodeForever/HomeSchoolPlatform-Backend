package com.dlut.vo;

import com.dlut.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {

    private String token;
    private UserInfoDto userInfoDto;

}
