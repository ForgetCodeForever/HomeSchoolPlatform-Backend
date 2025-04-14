package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorInfo {

    /** 专业id */
    @TableId
    private Long majorId;
    
    /** 书院名称 */
    private String academyName;
    
    /** 专业类别 */
    private String majorCategory;
    
    /** 专业名称 */
    private String majorName;
    
    /** 是否分流 0->未分流 1->已分流 */
    private String isDivert;
    
}
