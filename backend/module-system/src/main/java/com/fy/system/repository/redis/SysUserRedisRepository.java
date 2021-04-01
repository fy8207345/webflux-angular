package com.fy.system.repository.redis;

import com.fy.system.model.SysUser;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface SysUserRedisRepository extends KeyValueRepository<SysUser, Long> {

    List<SysUser> findByAccountEquals(String account);
}
