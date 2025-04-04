package com.dlut.service;

import com.dlut.entity.AdminMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AdminMenuService extends IService<AdminMenu> {

    List<AdminMenu> selectMenuTreeByUserId(Long userId);

}
