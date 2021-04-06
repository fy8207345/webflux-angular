package com.fy.system.model;

import com.fy.common.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(value = "sys_role")
public class SysRole extends BaseModel implements GrantedAuthority {

    @Id
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysRole sysRole = (SysRole) o;

        return id != null ? id.equals(sysRole.id) : sysRole.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
