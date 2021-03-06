package com.fy.system.controller;

import com.fy.common.model.ApiResult;
import com.fy.system.captcha.MyKaptcha;
import com.fy.system.model.CaptchaResponse;
import com.fy.system.model.CaptchaValidateResponse;
import com.fy.system.properties.CaptchaProperties;
import com.fy.system.service.CaptchaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(SysCaptchaController.MAPPING_PATH)
public class SysCaptchaController {

    public static final String MAPPING_PATH = "/captcha";

    private final @NonNull MyKaptcha myKaptchaProducer;
    private final @NonNull CaptchaService captchaService;
    private final @NonNull CaptchaProperties captchaProperties;

    private static final Pattern primaryColorExtractor = Pattern.compile("\\((.*)\\)");

    //primarg ： rgb(00, 00, 00)
    @RequestMapping
    public Mono<ApiResult<CaptchaResponse>> captcha(@RequestParam(value = "primary", required = false) String primary,
                                ServerWebExchange exchange) throws IOException {
        return Mono.create(monoSink -> {
            if(captchaProperties.isEnabled()){
                String colorString = extractColorString(primary);
                if(StringUtils.hasText(colorString)){
                    myKaptchaProducer.setColor(colorString);
                }
                String captchaText = myKaptchaProducer.createText();
                captchaService.save(exchange, captchaText).subscribe(success -> {
                    BufferedImage image = myKaptchaProducer.createImage(captchaText);
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
                });
            }else {
                monoSink.success();
            }
        });
    }

    @PostMapping("/validate")
    public Mono<CaptchaValidateResponse> validate(@RequestBody CaptchaResponse captchaResponse, ServerWebExchange exchange){
        return Mono.create(sink -> {

        });
    }

    private String extractColorString(String primary){
        Matcher matcher = primaryColorExtractor.matcher(primary);
        if(matcher.find()){
            String color = matcher.group(1);
            if(StringUtils.hasText(color)){
                String[] split = color.split(",");
                if(split.length == 3){
                    return Arrays.stream(split)
                            .map(String::trim)
                            .collect(Collectors.joining(","));
                }
            }
        }
        return null;
    }
}
