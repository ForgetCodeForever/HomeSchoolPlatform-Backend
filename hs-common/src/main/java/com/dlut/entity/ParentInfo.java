package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentInfo {

    /** 家长id */
    @TableId
    private Long parentId;
    
    /** 孩子学号 */
    private Long studentNumber;
    
    /** 密码 */
    private String password;
    
    /** 手机号码 */
    private String phoneNumber;
    
}
