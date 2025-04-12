package com.dlut.dto;

import lombok.Data;

@Data
public class ChangePasswordBodyDataDto {

    private String oldPassword;
    private String newPassword;

}
