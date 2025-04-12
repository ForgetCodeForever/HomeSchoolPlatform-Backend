package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.PageResult;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.AdminInfoDto;
import com.dlut.entity.AcademyInfo;
import com.dlut.entity.AdminRole;
import com.dlut.entity.AdminUser;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.AcademyInfoMapper;
import com.dlut.mapper.AdminRoleMapper;
import com.dlut.mapper.AdminUserMapper;
import com.dlut.service.AcademyInfoService;
import com.dlut.service.AdminUserService;
import com.dlut.utils.PasswordEncryptor;
import com.dlut.utils.UserThreadLocalUtil;
import com.dlut.vo.AdminUserListVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    public ResponseResult<?> batchRegister(MultipartFile file) {
        return null;
//        try {
//            List<AdminUserExcelDto> userList = EasyExcel.read(file.getInputStream())
//                    .head(AdminUserExcelDto.class)
//                    .sheet()
//                    .doReadSync();
//
//            // 校验、去重、过滤
//            if (CollectionUtils.isEmpty(userList)) {
//                return ResponseResult.errorResult("文件内容为空");
//            }
//
//            List<AdminUser> entities = userList.stream().map(dto -> {
//                AdminUser user = new AdminUser();
//                user.setUsername(dto.getUsername());
//                user.setPassword(PasswordEncryptor.encrypt(dto.getPassword())); // 建议加密
//                user.setAdminName(dto.getAdminName());
//                user.setAcademyId(dto.getAcademyId());
//                return user;
//            }).collect(Collectors.toList());
//
//            saveBatch(entities);
//            return ResponseResult.okResult().put("count", entities.size());
//        } catch (Exception e) {
//            log.error("导入失败", e);
//            return ResponseResult.errorResult("导入失败：" + e.getMessage());
//        }
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

}
