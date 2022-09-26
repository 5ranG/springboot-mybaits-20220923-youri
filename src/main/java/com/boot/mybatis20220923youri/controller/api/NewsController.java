package com.boot.mybatis20220923youri.controller.api;

import com.boot.mybatis20220923youri.domain.News;
import com.boot.mybatis20220923youri.dto.CMRespDto;
import com.boot.mybatis20220923youri.dto.NewsReadRespDto;
import com.boot.mybatis20220923youri.dto.NewsWriteReqDto;
import com.boot.mybatis20220923youri.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository; 
    //@RequiredArgsConstructor를 필수로 달아야함
    //@autowire대신 저걸 씀 이건 하나하나 다 달아야 하기 때문. 위는 최상단에 한번만 쓰면 됨

    @PostMapping("/news")
    public ResponseEntity<?> write(NewsWriteReqDto newsWriteReqDto){
        log.info("{}", newsWriteReqDto);

        News news = newsWriteReqDto.toEntity("홍이동"); //toEntity()메소드에서 객체 이미 만들어둠
        //newsWriteReqDto를 바로 toEntity로 만든 후 news에 대입
        int result = newsRepository.save(news);
        // namespace로 잡아뒀으므로 impl역할을 하는 .xml의 save메소드가 호출된다.

        if(result == 0){
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "새 글 작성 실패", news));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "새 글 작성 완료", news));
    }


    @GetMapping("/news/{newsId}")
    public ResponseEntity<?> read(@PathVariable int newsId){
        log.info("{}",newsId);

        News news = newsRepository.getNews(newsId);
        NewsReadRespDto newsReadRespDto = news.toDto();
        return ResponseEntity.ok(new CMRespDto<>(1, "게시글 불러오기 성공", newsReadRespDto));
    }

}
