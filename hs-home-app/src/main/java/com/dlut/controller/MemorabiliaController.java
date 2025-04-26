package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.MemorabiliaInfo;
import com.dlut.service.MemorabiliaInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memorabilia")
@RequiredArgsConstructor
public class MemorabiliaController {

    private final MemorabiliaInfoService memorabiliaInfoService;

    @GetMapping
    public ResponseResult<List<MemorabiliaInfo>> getAcademyMemorabiliaList(
            @RequestParam String academyName,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) String keyword
    ) {
        return memorabiliaInfoService.getAcademyMemorabiliaList(academyName, year, month, keyword);
    }
}
