package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.MemorabiliaInfo;
import com.dlut.service.MemorabiliaInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memorabilia")
public class MemorabiliaController {

    private final MemorabiliaInfoService memorabiliaInfoService;

    public MemorabiliaController(MemorabiliaInfoService memorabiliaInfoService) {
        this.memorabiliaInfoService = memorabiliaInfoService;
    }

    @PostMapping("/add")
    public ResponseResult<?> addMemorabilia(@RequestBody MemorabiliaInfo memorabiliaInfo) {
        return memorabiliaInfoService.addMemorabilia(memorabiliaInfo);
    }

    @GetMapping("/list")
    public ResponseResult<List<MemorabiliaInfo>> getAcademyMemorabiliaList(@RequestParam String academyName) {
        return memorabiliaInfoService.getAcademyMemorabiliaList(academyName);
    }

}
