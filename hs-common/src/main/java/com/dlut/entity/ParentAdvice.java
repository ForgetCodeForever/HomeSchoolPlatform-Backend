package com.dlut.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentAdvice {

    /** 建议id */
    @TableId
    private Long adviceId;
    
    /** 家长id */
    private Long parentId;
    
    /** 建议内容 */
    private String adviceContent;
    
    /** 管理员id */
    private Long adminId;
    
    /** 建议时间 */
    private LocalDateTime adviceTime;
    
    /** 回复 */
    private String replyContent;
    
    /** 回复时间 */
    private LocalDateTime replyTime;
    
    /** 建议状态 0->待处理 1->已处理 2->不合法 */
    private String status;

    @TableField(exist = false)
    private List<Long> adminIdList;
    
}
