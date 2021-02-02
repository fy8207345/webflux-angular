package com.fy.system.controller;

import com.fy.common.model.ApiResult;
import com.fy.system.model.CaptchaResponse;
import com.fy.system.model.CaptchaValidateResponse;
import com.fy.system.properties.CaptchaProperties;
import com.fy.system.service.CaptchaService;
import com.google.code.kaptcha.Producer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(SysCaptchaController.MAPPING_PATH)
public class SysCaptchaController {

    public static final String MAPPING_PATH = "/captcha";
    private static final String UNIQUE_DEVICE_HEAD_NAME = "UDID";

    private final @NonNull Producer producer;
    private final @NonNull CaptchaService captchaService;
    private final @NonNull CaptchaProperties captchaProperties;

    @GetMapping
    public Mono<ApiResult<CaptchaResponse>> captcha(ServerWebExchange exchange) throws IOException {
        return Mono.create(monoSink -> {
            if(captchaProperties.isEnabled()){
                ServerHttpRequest request = exchange.getRequest();
                String udid = getUniqueDviceId(request);
                if(!StringUtils.hasText(udid)){
                    monoSink.success(ApiResult.error("无效的请求"));
                    return;
                }
                String captchaText = producer.createText();
                captchaService.save(udid, captchaText).subscribe(success -> {
                    if(success){
                        BufferedImage image = producer.createImage(captchaText);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(image, "PNG", outputStream);
                        } catch (IOException e) {
                            monoSink.error(e);
                        }
                        byte[] bytes = outputStream.toByteArray();
                        String imageStr = Base64.getEncoder().encodeToString(bytes);
                        CaptchaResponse captchaResponse = new CaptchaResponse(imageStr, captchaProperties.isEnabled());
                        monoSink.success(ApiResult.success(captchaResponse));
                    }else{
                        monoSink.success(ApiResult.error("缓存验证码失败"));
                    }
                });
            }else {
                monoSink.success(ApiResult.success(new CaptchaResponse(null, captchaProperties.isEnabled())));
            }
        });
    }

    @PostMapping("/validate")
    public Mono<CaptchaValidateResponse> validate(@RequestBody CaptchaResponse captchaResponse, ServerWebExchange exchange){
        return Mono.create(sink -> {

        });
    }

    private String getUniqueDviceId(ServerHttpRequest request){
        HttpHeaders requestHeaders = request.getHeaders();
        return requestHeaders.getFirst(UNIQUE_DEVICE_HEAD_NAME);
    }
}
