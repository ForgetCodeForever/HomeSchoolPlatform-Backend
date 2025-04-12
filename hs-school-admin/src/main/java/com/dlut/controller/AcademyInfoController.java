package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.AcademyInfo;
import com.dlut.service.AcademyInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academy")
public class AcademyInfoController {

    private final AcademyInfoService academyInfoService;

    public AcademyInfoController(AcademyInfoService academyInfoService) {
        this.academyInfoService = academyInfoService;
    }

    @GetMapping
    public ResponseResult<List<AcademyInfo>> getAcademyList() {
        return academyInfoService.getAcademyList();
    }

}
