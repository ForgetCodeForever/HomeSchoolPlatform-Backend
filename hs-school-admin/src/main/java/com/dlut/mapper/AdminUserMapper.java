package com.dlut.mapper;

import com.dlut.entity.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    @Select("SELECT ar.role_name " +
            "FROM admin_role ar JOIN user_role ur ON ar.role_id = ur.role_id " +
            "JOIN admin_user au ON ur.admin_id = au.admin_id " +
            "WHERE au.username = #{username}")
    String findRoleNamesByUsername(@Param("username") String username);

}
