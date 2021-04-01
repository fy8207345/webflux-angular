package com.fy.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse {

    /**
     * 图片内容
     */
    private String image;

    /**
     * 是否启用验证码
     */
    private boolean enabled;

    /**
     * uuid
     */
    private String uuid;
}
