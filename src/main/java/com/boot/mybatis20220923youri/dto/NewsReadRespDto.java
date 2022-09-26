package com.boot.mybatis20220923youri.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewsReadRespDto {
    private int id;
    private String title;
    private String writer;
    private String broadcastingName;
    private String content;

    private LocalDateTime createDate;
    private String updateDate;
}
