package com.dlut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {

    private List<?> records;
    private Long total;
    private Long size;
    private Long current;
    private Long pages;

}