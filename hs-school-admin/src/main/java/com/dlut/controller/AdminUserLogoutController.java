package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.service.AdminUserLogoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserLogoutController {

    private final AdminUserLogoutService adminUserLogoutService;

    public AdminUserLogoutController(AdminUserLogoutService adminUserLogoutService) {
        this.adminUserLogoutService = adminUserLogoutService;
    }

    @PostMapping("/logout")
    public ResponseResult<?> logout() {
        return adminUserLogoutService.logout();
    }

}
