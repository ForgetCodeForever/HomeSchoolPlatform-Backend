package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AdminRoleService extends IService<AdminRole> {

    ResponseResult<List<AdminRole>> getAdminRoleList();
}
