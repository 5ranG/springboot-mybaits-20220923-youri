package com.boot.mybatis20220923youri.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration //설정객체
public class MvcConfig implements WebMvcConfigurer {
    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry); //default 세팅
        registry.addResourceHandler("/image/**") // http://localhost:8000/image/** 까지의 경로
                .addResourceLocations("file:///" + filePath) // 구체적 위치
                .setCachePeriod(60 * 60) // 1초 * 60 = 1시간 동안 캐쉬를 유지해라
                .resourceChain(true) //연결시켜주겠다
                .addResolver(new PathResourceResolver(){ //한글때문에 UTF_8 필요. 그런 역할
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8); 
                        return super.getResource(resourcePath, location);
                    }
                });
    }
}
