package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.MajorInfo;
import com.dlut.service.MajorInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/majorInfo")
@AllArgsConstructor
public class MajorInfoController {

    private final MajorInfoService majorInfoService;

    @GetMapping
    public ResponseResult<List<MajorInfo>> getMajorInfoList() {
        return majorInfoService.getMajorInfoList();
    }
}
