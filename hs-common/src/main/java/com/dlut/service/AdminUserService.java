package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface AdminUserService extends IService<AdminUser> {

    ResponseResult<?> register(AdminUser adminUser);

    ResponseResult<?> batchRegister(MultipartFile file);

    ResponseResult<?> getAcademyAdminUserList();

    ResponseResult<Boolean> checkUsername(String username);

    ResponseResult<?> deleteAdminUser(Long adminId);
}
