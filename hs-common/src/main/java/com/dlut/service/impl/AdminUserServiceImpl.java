package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.AdminInfoDto;
import com.dlut.dto.ChangePasswordBodyDataDto;
import com.dlut.entity.AcademyInfo;
import com.dlut.entity.AdminRole;
import com.dlut.entity.AdminUser;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.exception.SystemException;
import com.dlut.mapper.AcademyInfoMapper;
import com.dlut.mapper.AdminRoleMapper;
import com.dlut.mapper.AdminUserMapper;
import com.dlut.service.AcademyInfoService;
import com.dlut.service.AdminUserService;
import com.dlut.utils.ParentThreadLocalUtil;
import com.dlut.utils.PasswordEncryptor;
import com.dlut.utils.UserThreadLocalUtil;
import com.dlut.vo.AdminUserListVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    private final AdminUserMapper adminUserMapper;
    private final AcademyInfoService academyInfoService;
    private final AcademyInfoMapper academyInfoMapper;
    private final AdminRoleMapper adminRoleMapper;

    public AdminUserServiceImpl(AdminUserMapper adminUserMapper,
                                AcademyInfoService academyInfoService,
                                AcademyInfoMapper academyInfoMapper,
                                AdminRoleMapper adminRoleMapper
    ) {
        this.adminUserMapper = adminUserMapper;
        this.academyInfoService = academyInfoService;
        this.academyInfoMapper = academyInfoMapper;
        this.adminRoleMapper = adminRoleMapper;
    }

    @Override
    public ResponseResult<?> register(AdminUser adminUser) {
        // 表：书院id，无管理员角色
        // 传递数据：书院名称，管理员角色
        // 需填充user_role表
        // 1.查询书院id
        LambdaQueryWrapper<AcademyInfo> academyInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        academyInfoLambdaQueryWrapper.eq(AcademyInfo::getAcademyName, adminUser.getAcademyName());
        AcademyInfo academyInfo = academyInfoMapper.selectOne(academyInfoLambdaQueryWrapper);
        // 2.填充管理员表数据，密码加密，存入数据库
        adminUser.setAcademyId(academyInfo.getAcademyId());
        adminUser.setPassword(PasswordEncryptor.encrypt(adminUser.getPassword()));
        save(adminUser);
        // 3.查询角色id，填充user_role表
        LambdaQueryWrapper<AdminRole> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(AdminRole::getRoleName, adminUser.getRoleName());
        AdminRole adminRole = adminRoleMapper.selectOne(roleLambdaQueryWrapper);
        adminUserMapper.insertUserRole(adminUser.getAdminId(), adminRole.getRoleId());
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.REGISTER.getMsg());
    }

    @Override
    public ResponseResult<?> getAcademyAdminUserList() {
        // 1.查询当前负责人所属书院的成员
        // 获取当前负责人的书院id
        AdminInfoDto user = UserThreadLocalUtil.getUser();
        Long adminId = user.getAdminId();
        AdminUser adminUser = adminUserMapper.selectById(adminId);
        Long academyId = adminUser.getAcademyId();

        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getAcademyId, academyId);
        List<AdminUser> adminUserList = adminUserMapper.selectList(queryWrapper);

        // 2.如果当前用户是大工书院负责人，还要查询所有子书院负责人
        String roleName = user.getRoleName();
        if (SystemConstants.DA_GONG.equals(roleName)) {
            List<AdminUser> adminUsers = adminUserMapper.selectChildAcademyAdmins(SystemConstants.CHILD_ACADEMY);
            adminUserList.addAll(adminUsers);
        }

        // 3.转换成AdminUserListVo
        // 数据map
        List<AcademyInfo> academyInfos = academyInfoService.getAcademyList().getData();
        Map<Long, String> academyInfoMap = academyInfos.stream()
                .collect(Collectors.toMap(AcademyInfo::getAcademyId, AcademyInfo::getAcademyName));

        List<AdminUserListVo> adminUserListVos = adminUserList.stream()
                .map(item -> new AdminUserListVo(item.getAdminId(), item.getUsername(), item.getAdminName(), adminUserMapper.findRoleNameByUsername(item.getUsername()), academyInfoMap.get(item.getAcademyId())))
                .toList();
        return ResponseResult.okResult(adminUserListVos);
    }


    @Override
    public ResponseResult<Boolean> checkUsername(String username) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getUsername, username);
        return ResponseResult.okResult(count(queryWrapper) > 0);
    }

    @Override
    public ResponseResult<?> deleteAdminUser(Long adminId) {
        // 1.删除admin_user用户
        adminUserMapper.deleteById(adminId);
        // 2.删除user_role关系
        adminUserMapper.deleteUserRole(adminId);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.DEL.getMsg());
    }

    @Override
    public ResponseResult<?> changePassword(ChangePasswordBodyDataDto changePasswordBodyDataDto) {
        String oldPassword = changePasswordBodyDataDto.getOldPassword();
        Long adminId = UserThreadLocalUtil.getUser().getAdminId();
        AdminUser adminUser = adminUserMapper.selectById(adminId);
        if (!PasswordEncryptor.verify(oldPassword, adminUser.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.ORIGIN_PASSWORD_NOT_CORRECT);
        }
        String newPassword = changePasswordBodyDataDto.getNewPassword();
        adminUser.setPassword(PasswordEncryptor.encrypt(newPassword));
        adminUserMapper.updateById(adminUser);
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.CHANGE_PASSWORD.getMsg());
    }

    @Override
    public ResponseResult<?> getAdminUserList() {
        // 根据书院名查询书院id
        String academyName = ParentThreadLocalUtil.getUser().getAcademyName();
        LambdaQueryWrapper<AcademyInfo> academyInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        academyInfoLambdaQueryWrapper.eq(AcademyInfo::getAcademyName, academyName);
        AcademyInfo academyInfo = academyInfoMapper.selectOne(academyInfoLambdaQueryWrapper);
        // 查询当前书院所有管理员
        LambdaQueryWrapper<AdminUser> adminUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminUserLambdaQueryWrapper.eq(AdminUser::getAcademyId, academyInfo.getAcademyId());
        List<AdminUser> adminUsers = adminUserMapper.selectList(adminUserLambdaQueryWrapper);
        // 封装数据
        List<AcademyInfo> academyInfos = academyInfoService.getAcademyList().getData();
        Map<Long, String> academyInfoMap = academyInfos.stream()
                .collect(Collectors.toMap(AcademyInfo::getAcademyId, AcademyInfo::getAcademyName));

        List<AdminUserListVo> adminUserListVos = adminUsers.stream()
                .map(item -> new AdminUserListVo(item.getAdminId(), item.getUsername(), item.getAdminName(), adminUserMapper.findRoleNameByUsername(item.getUsername()), academyInfoMap.get(item.getAcademyId())))
                .toList();
        return ResponseResult.okResult(adminUserListVos);
    }

}
