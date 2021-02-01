package com.fy.system.controller;

import com.fy.system.service.CaptchaService;
import com.fy.system.utils.TokenUtils;
import com.google.code.kaptcha.Producer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(SysCaptchaController.MAPPING_PATH)
public class SysCaptchaController {

    public static final String MAPPING_PATH = "/captcha";

    private final @NonNull Producer producer;
    private final @NonNull CaptchaService captchaService;

    @GetMapping
    public Mono<DataBuffer> captcha(ServerWebExchange exchange) throws IOException {
        return Mono.create(monoSink -> {
            ServerHttpRequest request = exchange.getRequest();
            String text = producer.createText();
            captchaService.save(TokenUtils.getToken(request), text).subscribe(aBoolean -> {
                if(aBoolean){
                    ServerHttpResponse response = exchange.getResponse();
                    HttpHeaders headers = response.getHeaders();
                    headers.set("Expires", "0");
                    headers.set("Cache-Control", "no-store, no-cache, must-revalidate");
                    headers.set("Cache-Control", "post-check=0, pre-check=0");
                    headers.set("Pragma", "no-cache");
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    BufferedImage image = producer.createImage(text);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(image, "jpeg", outputStream);
                    } catch (IOException e) {
                        monoSink.error(e);
                    }
                    byte[] bytes = outputStream.toByteArray();
                    DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                    monoSink.success(dataBuffer);
                }else {
                    monoSink.error(new RuntimeException("缓存验证码失败"));
                }
            });
        });
    }
}
