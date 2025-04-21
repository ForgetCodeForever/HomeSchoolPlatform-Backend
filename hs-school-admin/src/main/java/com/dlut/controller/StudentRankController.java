package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.service.StudentRankService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/studentRank")
@AllArgsConstructor
public class StudentRankController {

    private final StudentRankService studentRankService;

    @PostMapping("/batchUpload")
    public ResponseResult<?> batchUpload(@RequestParam("file") MultipartFile file) {
        return studentRankService.batchUpload(file);
    }

    @GetMapping
    public ResponseResult<?> getStudentRankList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long majorId
    ) {
        return studentRankService.getStudentRankList(pageNum, pageSize, majorId);
    }
}
