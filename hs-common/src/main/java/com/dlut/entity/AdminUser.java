package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser {

    // 管理员id
    @TableId
    private Long adminId;
    
    /** 用户名 */
    private String username;
    
    /** 密码 */
    private String password;
    
    /** 管理员name */
    private String adminName;

    @TableField(exist = false)
    private String roleName;
    
    /** 书院id */
    private Long academyId;

    @TableField(exist = false)
    private String academyName;
    
}
