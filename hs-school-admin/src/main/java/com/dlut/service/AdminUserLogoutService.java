package com.dlut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;
import com.dlut.vo.LoginVo;

public interface AdminUserLogoutService extends IService<AdminUser> {

    ResponseResult<?> logout();

}
