package com.fy.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {

    public static final int SUCCESS = 0;
    /**
     * 默认错误码
     */
    public static final int DEFAULT_ERROR = 1;
    public static final int ERROR_CAPTCHA = 1001;

    private int code;
    private String msg;
    private T data;

    public static <T> ApiResult<T> success(T data){
        return new ApiResult<>(SUCCESS, null, data);
    }

    public static <T> ApiResult<T> success(){
        return new ApiResult<>(SUCCESS, null, null);
    }

    public static <T> ApiResult<T> error(String msg){
        return new ApiResult<>(DEFAULT_ERROR, msg, null);
    }

    public static <T> ApiResult<T> error(String msg, int code){
        return new ApiResult<>(code, msg, null);
    }
}
