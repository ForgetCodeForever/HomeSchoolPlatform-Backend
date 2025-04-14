package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.service.StudentInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/studentInfo")
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    public StudentInfoController(StudentInfoService studentInfoService) {
        this.studentInfoService = studentInfoService;
    }

    @PostMapping("/batchUpload")
    public ResponseResult<?> batchUpload(@RequestParam("file") MultipartFile file) {
        return studentInfoService.batchUpload(file);
    }

    @GetMapping
    public ResponseResult<?> getStudentInfoList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ) {
        return studentInfoService.getStudentInfoList(pageNum, pageSize);
    }

}
