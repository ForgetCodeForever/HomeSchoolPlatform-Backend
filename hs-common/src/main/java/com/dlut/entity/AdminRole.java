package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRole {

    /** 角色id */
    @TableId
    private Long roleId;
    
    /** 角色名 */
    private String roleName;
    
}
