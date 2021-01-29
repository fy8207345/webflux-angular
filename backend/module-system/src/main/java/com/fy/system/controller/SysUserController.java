package com.fy.system.controller;

import com.fy.system.model.SysUser;
import com.fy.system.service.SysUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class SysUserController {

    private final @NonNull SysUserService sysUserService;

    @GetMapping
    public Flux<SysUser> users(@RequestBody SysUser sysUser, @PageableDefault Pageable pageable){
        return sysUserService.list(sysUser, pageable);
    }

    @PostMapping
    public Mono<SysUser> insert(@RequestBody SysUser sysUser){
        return sysUserService.insert(sysUser);
    }

    @PutMapping
    public Mono<SysUser> update(@RequestBody SysUser sysUser){
        return sysUserService.update(sysUser);
    }

    @DeleteMapping
    public Mono<SysUser> delete(@RequestBody SysUser sysUser){
        return sysUserService.delete(sysUser);
    }
}
