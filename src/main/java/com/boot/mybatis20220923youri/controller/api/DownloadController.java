package com.boot.mybatis20220923youri.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequestMapping("/download")
@RestController
public class DownloadController {

    @Value("${file.path}")
    private String filePath;

    @GetMapping("/news")
    public ResponseEntity<?> download(@RequestParam String originFileName, @RequestParam String tempFileName) throws IOException {

        Path path = Paths.get(filePath + "news/" + tempFileName);
        String contentType = Files.probeContentType(path);
        //probeContentType 는 MIME형식으로 바꿔주는 역할

        log.info("ContentType: {}", contentType);

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(originFileName, StandardCharsets.UTF_8)
                .build(); // 원래 오리진파일명으로 다운받기 위함

        HttpHeaders headers = new HttpHeaders(); //header가 있어야 다운로드가 됨
        headers.setContentDisposition(contentDisposition);
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        // 외부에 있는 데이터를 InputStream 객체를 통해 자바에 입력하겠다.

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
