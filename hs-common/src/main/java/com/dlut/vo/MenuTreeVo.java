package com.dlut.vo;

import com.dlut.entity.AdminMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVo {

    private List<AdminMenu> menuTree;

}