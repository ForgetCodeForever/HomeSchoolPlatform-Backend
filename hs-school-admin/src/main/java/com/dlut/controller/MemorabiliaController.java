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

    @PostMapping
    public ResponseResult<?> addMemorabilia(@RequestBody MemorabiliaInfo memorabiliaInfo) {
        return memorabiliaInfoService.addMemorabilia(memorabiliaInfo);
    }

    @GetMapping
    public ResponseResult<List<MemorabiliaInfo>> getAcademyMemorabiliaList(
            @RequestParam String academyName,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) String keyword
    ) {
        return memorabiliaInfoService.getAcademyMemorabiliaList(academyName, year, month, keyword);
    }

    @PutMapping
    public ResponseResult<?> editMemorabilia(@RequestBody MemorabiliaInfo memorabiliaInfo) {
        return memorabiliaInfoService.editMemorabilia(memorabiliaInfo);
    }

    @GetMapping("/{memorabiliaId}")
    public ResponseResult<MemorabiliaInfo> getMemorabilia(@PathVariable Long memorabiliaId) {
        return memorabiliaInfoService.getMemorabilia(memorabiliaId);
    }

    @DeleteMapping("/{memorabiliaId}")
    public ResponseResult<?> deleteMemorabilia(@PathVariable Long memorabiliaId) {
        return memorabiliaInfoService.deleteMemorabilia(memorabiliaId);
    }

}
