package com.projectBackend.project.service;


import com.projectBackend.project.dto.TokenDto;
import com.projectBackend.project.dto.UserReqDto;
import com.projectBackend.project.dto.UserResDto;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.jwt.TokenProvider;
import com.projectBackend.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder; // 인증을 담당하는 클래스
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원 가입
    public UserResDto signup(UserReqDto requestDto) {
        if (userRepository.existsByUserEmail(requestDto.getUserEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toEntity(passwordEncoder);
        return UserResDto.of(userRepository.save(member));
    }

    // 로그인
    public TokenDto login(UserReqDto requestDto) {
        System.out.println("requestDto 이메일 :" + requestDto.getUserEmail());
        System.out.println("requestDto 패스워드 :" + requestDto.getUserPassword());
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        log.warn("authenticationToken: {}", authenticationToken);

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        log.warn("authentication: {}", authentication);

        return tokenProvider.generateTokenDto(authentication);
    }


    // 닉네임 중복 체크
    public boolean isNickName(String nickName) {
        System.out.println("닉네임 : " + nickName);
        boolean isTrue = userRepository.existsByUserNickname(nickName);
        log.warn("닉네임 중복 확인 {} : ", isTrue);
        return isTrue;
    }
}
