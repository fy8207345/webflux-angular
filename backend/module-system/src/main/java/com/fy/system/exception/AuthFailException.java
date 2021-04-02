package com.fy.system.exception;

import org.springframework.http.HttpStatus;

public class AuthFailException extends BaseException{

    public AuthFailException(){
        this("用户/密码不正确", HttpStatus.UNAUTHORIZED.value());
    }

    public AuthFailException(String message, int code) {
        super(message, code);
    }

    public AuthFailException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public AuthFailException(Throwable cause, int code) {
        super(cause, code);
    }

    public AuthFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
