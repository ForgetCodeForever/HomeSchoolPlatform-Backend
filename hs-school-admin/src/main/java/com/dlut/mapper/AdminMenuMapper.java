package com.dlut.mapper;

import com.dlut.entity.AdminMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

    @Select("SELECT DISTINCT m.menu_id, m.menu_name, m.parent_id, m.path, m.component, " +
            "                m.menu_type, IFNULL(m.permission, '') AS perms, m.icon\n" +
            "FROM user_role ur LEFT JOIN role_menu rm ON ur.role_id = rm.role_id\n" +
            "                  LEFT JOIN admin_menu m ON m.menu_id = rm.menu_id\n" +
            "WHERE ur.admin_id = #{userId} AND m.menu_type IN ('P', 'C')\n" +
            "ORDER BY m.parent_id")
    List<AdminMenu> selectMenusByUserId(Long userId);

}
