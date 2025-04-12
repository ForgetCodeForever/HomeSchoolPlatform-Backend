package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminRole;
import com.dlut.service.AdminRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    public AdminRoleController(AdminRoleService adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @GetMapping
    public ResponseResult<List<AdminRole>> getAdminRoleList() {
        return adminRoleService.getAdminRoleList();
    }

}
