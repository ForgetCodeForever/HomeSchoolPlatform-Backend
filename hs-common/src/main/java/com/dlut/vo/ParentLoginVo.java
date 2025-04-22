package com.dlut.vo;

import com.dlut.dto.ParentInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentLoginVo {

    private String token;
    private ParentInfoDto parentInfoDto;
}
