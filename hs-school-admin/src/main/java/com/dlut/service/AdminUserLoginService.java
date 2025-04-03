package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.vo.LoginVo;

public interface AdminUserLoginService extends IService<AdminUser> {

    ResponseResult<LoginVo> login(AdminUser adminUser);
}
