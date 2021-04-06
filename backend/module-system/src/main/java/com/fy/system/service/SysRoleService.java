package com.fy.system.service;

import com.fy.system.model.SysRole;
import com.fy.system.model.SysUserRole;
import com.fy.system.repository.r2dbc.SysRoleRepository;
import com.fy.system.repository.r2dbc.SysUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class SysRoleService {

    private final SysUserRoleRepository sysUserRoleRepository;
    private final SysRoleRepository sysRoleRepository;

    public Flux<SysRole> findByUserId(String userId){
        return sysUserRoleRepository.findByUserId(userId)
                .map(SysUserRole::getRoleId)
                .collectList()
                .flatMapMany(sysRoleRepository::findAllById);
    }
}
