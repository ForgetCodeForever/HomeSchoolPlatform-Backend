package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminUser")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseResult<?> getAdminUserList() {
        return adminUserService.getAdminUserList();
    }
}
