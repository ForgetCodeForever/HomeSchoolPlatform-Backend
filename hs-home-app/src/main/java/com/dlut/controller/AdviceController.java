package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.ParentAdvice;
import com.dlut.service.ParentAdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advice")
@RequiredArgsConstructor
public class AdviceController {

    private final ParentAdviceService parentAdviceService;

    @PostMapping
    public ResponseResult<?> addAdvice(@RequestBody ParentAdvice parentAdvice) {
        return parentAdviceService.addAdvice(parentAdvice);
    }

    @GetMapping
    public ResponseResult<?> getAdviceList() {
        return parentAdviceService.getParentAdviceList();
    }
}
