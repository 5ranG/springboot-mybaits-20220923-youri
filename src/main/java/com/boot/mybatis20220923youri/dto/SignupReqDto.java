package com.boot.mybatis20220923youri.dto;

import com.boot.mybatis20220923youri.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class SignupReqDto {
    //private Integer userCode; // ResponseDto에서는 필요하다. 그떄는 생성되기 떄문에
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;

    public User toEntity(){
        return User.builder()
                //.user_code(userCode)
                .user_id(userId)
                .user_password(userPassword)
                .user_name(userName)
                .user_email(userEmail)
                .build();
    }
}
