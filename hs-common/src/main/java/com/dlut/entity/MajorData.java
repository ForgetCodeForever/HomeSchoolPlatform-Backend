package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorData {

    /** 数据id */
    @TableId
    private Long dataId;
    
    /** 专业id */
    private Long majorId;
    
    /** 专业人数 */
    private Integer totalStudents;
    
    /** 保研率 */
    private Integer graduateRate;
    
    /** 20xx级 */
    private Integer year;

}
