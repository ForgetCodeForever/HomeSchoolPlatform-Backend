package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminUserLoginService extends IService<AdminUser> {

    ResponseResult<?> login(AdminUser adminUser);
}
