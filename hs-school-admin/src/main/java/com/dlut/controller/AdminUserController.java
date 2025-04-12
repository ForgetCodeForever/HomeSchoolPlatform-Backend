package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.dto.ChangePasswordBodyDataDto;
import com.dlut.entity.AdminUser;
import com.dlut.service.AdminUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping
    public ResponseResult<?> register(@RequestBody AdminUser adminUser) {
        return adminUserService.register(adminUser);
    }

    @GetMapping
    public ResponseResult<?> getAcademyAdminUserList() {
        return adminUserService.getAcademyAdminUserList();
    }

    @GetMapping("/checkUsername")
    public ResponseResult<Boolean> checkUsername(@RequestParam("username") String username) {
        return adminUserService.checkUsername(username);
    }

    @DeleteMapping("/{adminId}")
    public ResponseResult<?> deleteAdminUser(@PathVariable("adminId") Long adminId) {
        return adminUserService.deleteAdminUser(adminId);
    }

    @PostMapping("/changePassword")
    public ResponseResult<?> changePassword(@RequestBody ChangePasswordBodyDataDto changePasswordBodyDataDto) {
        return adminUserService.changePassword(changePasswordBodyDataDto);
    }

}
