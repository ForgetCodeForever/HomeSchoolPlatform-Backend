package com.dlut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;

public interface AdminUserLogoutService extends IService<AdminUser> {

    ResponseResult<?> logout();

}
