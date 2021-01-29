package com.fy.system.repository;

import com.fy.common.repository.BaseRepository;
import com.fy.system.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {
}
