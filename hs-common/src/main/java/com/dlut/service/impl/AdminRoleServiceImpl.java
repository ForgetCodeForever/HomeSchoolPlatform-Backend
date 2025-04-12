package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.dto.AdminInfoDto;
import com.dlut.entity.AdminRole;
import com.dlut.mapper.AdminRoleMapper;
import com.dlut.service.AdminRoleService;
import com.dlut.utils.CurrentUserUtils;
import com.dlut.utils.UserThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

    private final AdminRoleMapper adminRoleMapper;
    private final CurrentUserUtils currentUserUtils;

    public AdminRoleServiceImpl(AdminRoleMapper adminRoleMapper, CurrentUserUtils currentUserUtils) {
        this.adminRoleMapper = adminRoleMapper;
        this.currentUserUtils = currentUserUtils;
    }

    @Override
    public ResponseResult<List<AdminRole>> getAdminRoleList() {
        // 获取当前用户的角色id，只显示大于角色id的角色
        Long currentAdminRoleId = currentUserUtils.getCurrentAdminRoleId();
        LambdaQueryWrapper<AdminRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(AdminRole::getRoleId, currentAdminRoleId);
        List<AdminRole> adminRoles = adminRoleMapper.selectList(queryWrapper);
        return ResponseResult.okResult(adminRoles);
    }
}
