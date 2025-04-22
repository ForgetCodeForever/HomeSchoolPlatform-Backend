package com.dlut.controller;

import com.dlut.ResponseResult;
import com.dlut.entity.ParentInfo;
import com.dlut.service.ParentLoginService;
import com.dlut.vo.ParentLoginVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ParentLoginController {

    private final ParentLoginService parentLoginService;

    @PostMapping("/login")
    public ResponseResult<ParentLoginVo> login(@RequestBody ParentInfo parentInfo) {
        return parentLoginService.login(parentInfo);
    }

}
