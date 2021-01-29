package com.fy.system.service;

import com.fy.common.service.BaseService;
import com.fy.system.model.SysUser;
import com.fy.system.repository.SysUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SysUserService extends BaseService {


    private final @NonNull R2dbcEntityTemplate r2dbcEntityTemplate;
    private final @NonNull SysUserRepository sysUserRepository;

    @Transactional
    public Mono<SysUser> insert(SysUser sysUser){
        return r2dbcEntityTemplate.insert(sysUser);
    }

    @Transactional
    public Mono<SysUser> update(SysUser sysUser){
        return r2dbcEntityTemplate.update(sysUser);
    }

    @Transactional
    public Mono<SysUser> delete(SysUser sysUser){
        return r2dbcEntityTemplate.delete(sysUser);
    }

    public Flux<SysUser> list(SysUser sysUser, Pageable pageable){
        return r2dbcEntityTemplate.select(SysUser.class)
                .matching(
                    query(
                        pageable,
                        like("account", sysUser.getAccount())
                    )
                )
                .all();
    }
}
