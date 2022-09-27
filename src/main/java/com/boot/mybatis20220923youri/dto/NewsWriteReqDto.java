package com.boot.mybatis20220923youri.dto;

import com.boot.mybatis20220923youri.domain.News;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NewsWriteReqDto {
    private String title; //이건 from의 name과 동일
    private String broadcastingName;
    private String writer;
    private String content;
    private List<MultipartFile> files;

    public News toEntity(String writer) {
        return News.builder()
                .news_title(title)
                .news_writer(writer)
                .news_broadcasting(broadcastingName)
                .news_content(content)
                .build();
    }
}