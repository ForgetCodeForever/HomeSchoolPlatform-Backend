package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;
import com.dlut.service.AdminUserLoginService;
import com.dlut.service.AdminUserLogoutService;
import com.dlut.vo.LoginVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserLogoutController {

    private final AdminUserLogoutService adminUserLogoutService;

    public AdminUserLogoutController(AdminUserLogoutService adminUserLogoutService) {
        this.adminUserLogoutService = adminUserLogoutService;
    }

    @PostMapping("/logout")
    public ResponseResult<?> login() {
        return adminUserLogoutService.logout();
    }

}
