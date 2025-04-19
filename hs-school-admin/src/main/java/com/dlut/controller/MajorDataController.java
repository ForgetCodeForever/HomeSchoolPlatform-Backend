package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.MajorData;
import com.dlut.service.MajorDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/majorData")
@AllArgsConstructor
public class MajorDataController {

    private final MajorDataService majorDataService;

    @PostMapping
    public ResponseResult<?> addMajorData(@RequestBody MajorData majorData) {
        return majorDataService.addMajorData(majorData);
    }

    @GetMapping
    public ResponseResult<?> getMajorDataList(@RequestParam(required = false) Long majorId) {
        return majorDataService.getMajorDataList(majorId);
    }

    @PutMapping
    public ResponseResult<?> editMajorData(@RequestBody MajorData majorData) {
        return majorDataService.editMajorData(majorData);
    }

    @GetMapping("/{dataId}")
    public ResponseResult<?> getMajorData(@PathVariable Long dataId) {
        return majorDataService.getMajorData(dataId);
    }

    @DeleteMapping("/{dataId}")
    public ResponseResult<?> deleteMajorData(@PathVariable Long dataId) {
        return majorDataService.deleteMajorData(dataId);
    }
}
