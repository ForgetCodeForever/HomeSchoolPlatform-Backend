package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.AdminMenu;
import com.dlut.entity.AdminUser;
import com.dlut.service.AdminMenuService;
import com.dlut.service.AdminUserLoginService;
import com.dlut.utils.UserThreadLocalUtil;
import com.dlut.vo.LoginVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminUserLoginController {

    private final AdminUserLoginService adminUserLoginService;
    private final AdminMenuService adminMenuService;

    public AdminUserLoginController(AdminUserLoginService adminUserLoginService, AdminMenuService adminMenuService) {
        this.adminUserLoginService = adminUserLoginService;
        this.adminMenuService = adminMenuService;
    }

    @PostMapping("/login")
    public ResponseResult<LoginVo> login(@RequestBody AdminUser adminUser) {
        return adminUserLoginService.login(adminUser);
    }

//    @GetMapping("/getPermissions")
//    public ResponseResult<AdminUserInfoVo> getInfo() {
//        // {"code", "data{""permissions", "roles", "user"}, "msg"}
//        // 获取当前登录用户
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//
//        // 根据用户ID查询权限信息
//        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
//
//        // 根据用户ID查询角色信息
//        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
//
//        // 获取用户信息-为封装数据第三个参数服务
//        User user = loginUser.getUser();
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
//
//        // 封装数据返回
//        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
//        return ResponseResult.okResult(adminUserInfoVo);
//    }

    @GetMapping("/getMenuTree")
    public ResponseResult<?> getMenuTree() {
        Long userId = UserThreadLocalUtil.getUser().getUserId();
        List<AdminMenu> menuTree = adminMenuService.selectMenuTreeByUserId(userId);
        return ResponseResult.okResult(menuTree);
    }

}
