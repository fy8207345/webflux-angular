package com.fy.system.service;

import com.fy.common.service.BaseService;
import com.fy.system.model.SysUser;
import com.fy.system.repository.SysUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
        List<Criteria> criterias = new ArrayList<>();
        if(StringUtils.hasText(sysUser.getAccount())){
            criterias.add(like("account", sysUser.getAccount()));
        }
        return r2dbcEntityTemplate.select(SysUser.class)
                .matching(
                    query(
                        pageable,
                        criterias.toArray(new Criteria[0])
                    )
                )
                .all();
    }
}
