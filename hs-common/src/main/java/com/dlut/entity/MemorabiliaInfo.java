package com.dlut.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemorabiliaInfo {

    /** 大事记id */
    @TableId
    private Long memorabiliaId;
    
    /** 大事记时间 */
    private Date memorabiliaTime;
    
    /** 大事记内容 */
    private String memorabiliaContent;
    
    /** 大事记所属书院 */
    private String academyName;
    
}
