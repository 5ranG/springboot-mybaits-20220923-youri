package com.boot.mybatis20220923youri.controller.api;

import com.boot.mybatis20220923youri.domain.User;
import com.boot.mybatis20220923youri.dto.CMRespDto;
import com.boot.mybatis20220923youri.dto.SignupReqDto;
import com.boot.mybatis20220923youri.dto.SignupRespDto;
import com.boot.mybatis20220923youri.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor //생성자를 만들어줌. Noarg /allarg 없음.
// 매개변수가 무조건 필요하다고 달려있는 것. IoC에서 짝이 맞는 애를 찾아줌.
// 그래서 @Autowired가 생략하고 [@RequiredArgsConstructor ]와 함께 private final UserRepository userRepository; 가 쓰였다.
public class AuthController {
    private final UserRepository userRepository;
    // 상수는 무조건 선언 후에 초기화를 해주어야한다.
    // 초기화를 해주는 녀석이 없어서 에러가 뜬다.

    /*
        @Autowired
        private UserRepository = userRepository;
    */

    @PostMapping("/signup")
    public ResponseEntity<?> signup(SignupReqDto signupDto){
        log.info("{}", signupDto);

        User user = signupDto.toEntity();

//        try {
//            throw new RuntimeException("강제로 예외 발생");
//        }catch (Exception e){
//            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"회원가입 실패",true));
//        }
        
        log.info("마이바티스.xml 가기 전 Entity: {}", user);
        int result = userRepository.save(user);
        log.info("마이바티스.xml 다녀온 후 Entity: {}", user);

        SignupRespDto signupRespDto = user.toDto();

        if(result == 0){
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"회원가입 실패",signupRespDto));
        }
        return ResponseEntity.ok(new CMRespDto<>(1,"회원가입 완료",signupRespDto));
    }
}
