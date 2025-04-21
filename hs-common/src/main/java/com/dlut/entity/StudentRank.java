package com.dlut.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRank {

    /** 学号 */
    @TableId
    private Long studentNumber;
    
    /** 大一上排名 */
    private Integer rank1;
    
    /** 大一下排名 */
    private Integer rank2;
    
    /** 大二上排名 */
    private Integer rank3;
    
    /** 大二下排名 */
    private Integer rank4;
    
    /** 大三上排名 */
    private Integer rank5;
    
    /** 大三下排名 */
    private Integer rank6;
    
}
