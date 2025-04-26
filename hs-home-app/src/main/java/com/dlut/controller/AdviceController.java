package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.ParentAdvice;
import com.dlut.service.ParentAdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advice")
@RequiredArgsConstructor
public class AdviceController {

    private final ParentAdviceService parentAdviceService;

    @PostMapping
    public ResponseResult<?> addAdvice(@RequestBody ParentAdvice parentAdvice) {
        return parentAdviceService.addAdvice(parentAdvice);
    }
}
