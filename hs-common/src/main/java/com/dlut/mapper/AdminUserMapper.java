package com.dlut.mapper;

import com.dlut.entity.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    @Select("SELECT ar.role_name " +
            "FROM admin_role ar JOIN user_role ur ON ar.role_id = ur.role_id " +
            "                   JOIN admin_user au ON ur.admin_id = au.admin_id " +
            "WHERE au.username = #{username}")
    String findRoleNameByUsername(@Param("username") String username);

    @Select("SELECT ar.role_id " +
            "FROM admin_role ar JOIN user_role ur ON ar.role_id = ur.role_id " +
            "                   JOIN admin_user au ON ur.admin_id = au.admin_id " +
            "WHERE au.admin_id = #{adminId}")
    Long findRoleIdByAdminId(@Param("adminId") Long adminId);

    @Select("SELECT au.*" +
            "FROM admin_user au JOIN user_role ur ON au.admin_id = ur.admin_id " +
            "WHERE ur.role_id = #{roleId}")
    List<AdminUser> selectChildAcademyAdmins(@Param("roleId") Long roleId);

    @Insert("INSERT INTO user_role (admin_id, role_id) VALUES (#{adminId}, #{roleId})")
    void insertUserRole(@Param("adminId") Long adminId, @Param("roleId") Long roleId);

    @Delete("DELETE FROM user_role WHERE admin_id = #{adminId}")
    void deleteUserRole(@Param("adminId") Long adminId);
}
