package com.fy.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseModel implements Serializable {

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 创建人
     */
    @CreatedBy
    private String createBy;

    /**
     * 更新人
     */
    @LastModifiedBy
    private String updateBy;

    /**
     * 版本
     */
    @Version
    private Long version;
}
