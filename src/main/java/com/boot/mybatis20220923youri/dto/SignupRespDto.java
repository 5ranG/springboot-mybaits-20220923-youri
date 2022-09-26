package com.boot.mybatis20220923youri.dto;

import com.boot.mybatis20220923youri.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupRespDto {
    private int userCode;
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
}
