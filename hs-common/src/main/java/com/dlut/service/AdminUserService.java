package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.dto.ChangePasswordBodyDataDto;
import com.dlut.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface AdminUserService extends IService<AdminUser> {

    ResponseResult<?> register(AdminUser adminUser);

    ResponseResult<?> getAcademyAdminUserList();

    ResponseResult<Boolean> checkUsername(String username);

    ResponseResult<?> deleteAdminUser(Long adminId);

    ResponseResult<?> changePassword(ChangePasswordBodyDataDto changePasswordBodyDataDto);

    ResponseResult<?> getAdminUserList();
}
