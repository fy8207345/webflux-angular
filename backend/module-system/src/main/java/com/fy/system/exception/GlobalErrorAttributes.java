package com.fy.system.exception;

import com.fy.common.model.ApiResult;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    public static final String RESULT_KEY = "GlobalErrorAttributes.RESULT_KEY";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);

        Throwable error = getError(request);

        int code = 500;
        if(error instanceof AuthenticationException){
            code = HttpStatus.UNAUTHORIZED.value();
        } else if(error instanceof BaseException){
            BaseException exception = (BaseException) error;
            code = exception.getCode();
        }
        errorAttributes.put(RESULT_KEY, ApiResult.error(error.getMessage(), code));
        return errorAttributes;
    }
}
