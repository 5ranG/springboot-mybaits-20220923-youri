package com.boot.mybatis20220923youri.dto;

import com.boot.mybatis20220923youri.domain.NewsFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsWriteRespDto {
    private int id;
    private String title;
    private String writer;
    private String broadcastingName;
    private String content;

    private List<NewsFile> newsFileList;
}
