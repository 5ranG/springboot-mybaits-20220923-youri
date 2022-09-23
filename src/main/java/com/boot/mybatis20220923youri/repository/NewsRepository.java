package com.boot.mybatis20220923youri.repository;

import com.boot.mybatis20220923youri.domain.News;
import org.apache.ibatis.annotations.Mapper;

@Mapper//news.xml과 연결되어있다. 이게 impl 역할중
//이게 IOC 등록역할도 해주고 있다. 그러므로 imlp에 하던 어노테이션을 기입을 X
public interface NewsRepository {
    public int save(News news); // art+enter 
    // news.xml로 던져줬다.
}
