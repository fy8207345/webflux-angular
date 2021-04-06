package com.fy.system.repository.r2dbc;

import com.fy.common.repository.BaseRepository;
import com.fy.system.model.SysRole;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, String> {
}
