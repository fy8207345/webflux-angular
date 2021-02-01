package com.fy.system.filter;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class TokenGenerator {

    public Serializable generate(){
        return UUID.randomUUID().toString();
    }
}
