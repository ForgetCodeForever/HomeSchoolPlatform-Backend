package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;
import com.dlut.service.AdminUserLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserLoginController {

    private final AdminUserLoginService adminUserLoginService;

    public AdminUserLoginController(AdminUserLoginService adminUserLoginService) {
        this.adminUserLoginService = adminUserLoginService;
    }

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody AdminUser adminUser) {
        return adminUserLoginService.login(adminUser);
    }



}
