package com.fy.system.model;

import com.fy.common.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table("sys_user_role")
public class SysUserRole extends BaseModel {

    @Id
    private String id;

    private String userId;

    private String roleId;
}
