package com.boot.mybatis20220923youri.controller.api;

import com.boot.mybatis20220923youri.domain.News;
import com.boot.mybatis20220923youri.domain.NewsFile;
import com.boot.mybatis20220923youri.dto.CMRespDto;
import com.boot.mybatis20220923youri.dto.NewsReadRespDto;
import com.boot.mybatis20220923youri.dto.NewsWriteReqDto;
import com.boot.mybatis20220923youri.dto.NewsWriteRespDto;
import com.boot.mybatis20220923youri.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class NewsController {

    //@Value("파일 경로")
    @Value("${file.path}") //aplication.yml 경로가지고옴 @authowired같은 역할
    private String filePath;

    private final NewsRepository newsRepository; 
    //@RequiredArgsConstructor를 필수로 달아야함
    //@autowire대신 저걸 씀 이건 하나하나 다 달아야 하기 때문. 위는 최상단에 한번만 쓰면 됨

    @PostMapping("/news")
    public ResponseEntity<?> write(NewsWriteReqDto newsWriteReqDto) {
        log.info("{}", newsWriteReqDto);

        List<NewsFile> newsFileList = null;

        MultipartFile firstFile = newsWriteReqDto.getFiles().get(0);
        String firstFileName = firstFile.getOriginalFilename();
        //스프링 기본파일 1개를 가지고 있다.

        log.info("{}", filePath);

        if(!firstFileName.isBlank()) {
            log.info("파일 입출력을 합니다.");

            newsFileList = new ArrayList<NewsFile>();

            for(MultipartFile file : newsWriteReqDto.getFiles()){
                String originFileName = file.getOriginalFilename();
                //log.info("fileName: {}", file.getOriginalFilename());
                //파일 이름들을 하나씩 출력한다.
                String uuid = UUID.randomUUID().toString(); //파일명이 겹치면 안되므로 사용
                String extension = originFileName.substring(originFileName.lastIndexOf(".")); //확장자명 가져오기
                String tempFileName = uuid + extension;

                Path uploadPath = Paths.get(filePath, "news/" + tempFileName);
                //log.info("파일 업로드 경로: {}", uploadPath.toString());

                File f = new File(filePath + "news");
                if(!f.exists()){ // 저 경로(폴더)가 없을때
                    f.mkdirs(); //모든 경로를 다 만들어줌...
                }

                try {
                    Files.write(uploadPath, file.getBytes());
                } catch (IOException e) { //파일이 있을수도있고, 없을수도 있어서 예외
                    throw new RuntimeException(e);
                }

                NewsFile newsFile = NewsFile.builder()
                        .file_origin_name(originFileName)
                        .file_temp_name(tempFileName)
                        .build();

                newsFileList.add(newsFile);
            }
        }

        News news = newsWriteReqDto.toEntity("김준일");//toEntity()메소드에서 객체 이미 만들어둠
        //newsWriteReqDto를 바로 toEntity로 만든 후 news에 대입
        int result = newsRepository.save(news);
        // namespace로 잡아뒀으므로 impl역할을 하는 .xml의 save메소드가 호출된다.

        if(result == 0) {
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "새 글 작성 실패", news));
        }

        if(newsFileList != null) {
            for(NewsFile newsFile : newsFileList) {
                newsFile.setNews_id(news.getNews_id());
                log.info("NewsFile 객체: {}", newsFile);
            }
            result = newsRepository.saveFiles(newsFileList);

            if(result != newsFileList.size()) {
                return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "파일 업로드 실패", newsFileList));
            }
        }


        NewsWriteRespDto newsWriteRespDto = news.toNewsWriteRespDto(newsFileList);
        return ResponseEntity.ok(new CMRespDto<>(1, "새 글 작성 완료", newsWriteRespDto));
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<?> read(@PathVariable int newsId) {
        log.info("{}", newsId);
        News news = newsRepository.getNews(newsId);
        NewsReadRespDto newsReadRespDto = news.toNewsReadRespDto();
        return ResponseEntity.ok(new CMRespDto<>(1, "게시글 불러오기 성공", newsReadRespDto));
    }

}