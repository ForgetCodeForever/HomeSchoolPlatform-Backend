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

    @GetMapping
    public ResponseResult<?> getAdviceList() {
        return parentAdviceService.getAdviceList();
    }

    @PutMapping
    public ResponseResult<?> replyAdvice(@RequestBody ParentAdvice parentAdvice) {
        return parentAdviceService.replyAdvice(parentAdvice);
    }
}
