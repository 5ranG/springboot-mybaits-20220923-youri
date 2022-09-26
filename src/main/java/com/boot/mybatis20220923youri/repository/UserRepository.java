package com.boot.mybatis20220923youri.repository;

import com.boot.mybatis20220923youri.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    public int save(User user);
}
