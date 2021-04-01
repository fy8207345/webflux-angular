package com.fy.system.repository.r2dbc;

import com.fy.common.repository.BaseRepository;
import com.fy.system.model.SysUser;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {
    Mono<SysUser> findFirstByAccount(String account);
}
