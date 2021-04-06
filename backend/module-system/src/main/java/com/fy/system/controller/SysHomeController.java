package com.fy.system.controller;

import com.fy.common.controller.BaseController;
import com.fy.common.model.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/home")
public class SysHomeController extends BaseController {

    @GetMapping
    public Mono<ApiResult<Object>> home(){
        return Mono.just(ApiResult.success());
    }
}
