package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminMenu {

    /** 菜单id */
    @TableId
    private Long menuId;
    
    /** 菜单名称 */
    private String menuName;
    
    /** 父菜单id */
    private Long parentId;
    
    /** 路由地址 */
    private String path;
    
    /** 组件地址 */
    private String component;
    
    /** 菜单类型 父目录(P) 子目录(C) 按钮(B) */
    private String menuType;
    
    /** 权限标识 */
    private String permission;
    
    /** 菜单图标 */
    private String icon;
    
}
