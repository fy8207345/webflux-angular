package com.fy.system.config;

import com.fy.system.captcha.MyKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public MyKaptcha captchaProducer(){
        MyKaptcha myKaptcha = new MyKaptcha();
        Properties properties = MyKaptcha.getConfigProperties();
        myKaptcha.setConfig(new Config(properties));
        return myKaptcha;
    }
}
