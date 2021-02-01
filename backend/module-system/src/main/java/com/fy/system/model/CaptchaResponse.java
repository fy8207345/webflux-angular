package com.fy.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse {
    /**
     * 图片内容
     */
    private DataBuffer dataBuffer;
    /**
     * 根据客户端生成的加密信息
     */
    private String secret;
}
