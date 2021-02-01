package com.fy.system.model;

import lombok.*;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
public class Captcha implements Serializable {

    private String token;

    private String captcha;

    @TimeToLive
    private Long timeToLive;
}
