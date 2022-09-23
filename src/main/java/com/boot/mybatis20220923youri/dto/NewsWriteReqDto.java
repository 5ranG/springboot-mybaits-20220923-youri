package com.boot.mybatis20220923youri.dto;

import com.boot.mybatis20220923youri.domain.News;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NewsWriteReqDto {
    private String title;
    private String broadcasting;
    private String writer;
    private String content;
    private List<MultipartFile> files;

    public News toEntity(){ //News객체를 return해줌
        return News.builder() //뉴스 객체 생성 선언
                .news_title(title) //어떤 변수에 어떤 걸 넣을건지에 대한 setter
                .news_writer(writer)
                .news_broadcasting(broadcasting)
                .content(content)
                .build();
    }
}
