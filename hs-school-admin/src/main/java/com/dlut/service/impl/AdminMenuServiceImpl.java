package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.entity.AdminMenu;
import com.dlut.mapper.AdminMenuMapper;
import com.dlut.service.AdminMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements AdminMenuService {

    private final AdminMenuMapper adminMenuMapper;

    public AdminMenuServiceImpl(AdminMenuMapper adminMenuMapper) {
        this.adminMenuMapper = adminMenuMapper;
    }

    @Override
    public List<AdminMenu> selectMenuTreeByUserId(Long userId) {
        List<AdminMenu> menus = adminMenuMapper.selectMenusByUserId(userId);
        List<AdminMenu> menuTree = buildMenuTree(menus, 0L);
        return menuTree;
    }

    // 先找出第一层的菜单，然后将他们的子菜单设置到children属性中
    private List<AdminMenu> buildMenuTree(List<AdminMenu> menus, Long parentId) {
        List<AdminMenu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    // 获取父菜单的子菜单
    private List<AdminMenu> getChildren(AdminMenu parentMenu, List<AdminMenu> menus) {
        List<AdminMenu> childrenList = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentMenu.getMenuId()))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

}
