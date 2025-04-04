package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademyInfo {

    /** 书院id */
    @TableId
    private Long academyId;
    
    /** 书院名称 */
    private String academyName;
    
}
