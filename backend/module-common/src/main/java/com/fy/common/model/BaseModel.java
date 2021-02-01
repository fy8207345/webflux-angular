package com.fy.common.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @Transient
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Transient
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "Asia/Shanghai")
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

    //不序列化该字段
    @JsonIgnore
    public LocalDateTime getStartTime() {
        return startTime;
    }

    //需要反序列号该字段
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-DD", timezone = "Asia/Shanghai")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @JsonIgnore
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-DD", timezone = "Asia/Shanghai")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
