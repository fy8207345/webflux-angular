package com.fy.system.repository.r2dbc;

import com.fy.common.repository.BaseRepository;
import com.fy.system.model.SysUserRole;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SysUserRoleRepository extends BaseRepository<SysUserRole, String> {

    Flux<SysUserRole> findByUserId(String userId);

}
