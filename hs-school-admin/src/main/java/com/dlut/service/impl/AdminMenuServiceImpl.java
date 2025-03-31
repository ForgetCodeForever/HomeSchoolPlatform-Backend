package com.dlut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.entity.AdminMenu;
import com.dlut.mapper.AdminMenuMapper;
import com.dlut.service.AdminMenuService;
import org.springframework.stereotype.Service;

@Service
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements AdminMenuService {
    
}
