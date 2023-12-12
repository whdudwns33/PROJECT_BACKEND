package com.projectBackend.project.service;


import com.projectBackend.project.dto.UserReqDto;
import com.projectBackend.project.dto.UserResDto;
import com.projectBackend.project.dto.TokenDto;
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

import java.util.UUID;

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
        // 인증 코드 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        log.warn("authenticationToken: {}", authenticationToken);

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        log.warn("authentication: {}", authentication);

        return tokenProvider.generateTokenDto(authentication);
    }

    // 카카오 로그인 => 카카오 토큰이 존재하지만, 사용하지 않을 생각
    public TokenDto kakaoLogin(String email) {
        if (email != null) {
            // 랜덤 비밀번호 생성
            String password = generateRandomPassword();
            UserReqDto userReqDto = new UserReqDto();
            userReqDto.setUserEmail(email);
            userReqDto.setUserPassword(password);
            UsernamePasswordAuthenticationToken authenticationToken = userReqDto.toAuthentication();
            log.info("승인 토큰 : {}", authenticationToken);
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            log.info("승인 정보 : {}", authentication);
            return tokenProvider.generateTokenDto(authentication);
        }
        else {
            return null;
        }
    }


    // 카카오 로그인 이후 랜덤 패스워드 생성
    public String generateRandomPassword() {
        UUID uuid = UUID.randomUUID();

        // UUID를 문자열로 변환하고 '-' 제거
        String uuidAsString = uuid.toString().replaceAll("-", "");

        // 앞에서부터 10자리만 선택하여 랜덤 비밀번호로 사용
        String randomPassword = uuidAsString.substring(0, 10);

        return randomPassword;
    }

    // 이메일 중복 체크
    public boolean isEmail (String email) {
        if (email != null) {
            boolean isTrue = userRepository.existsByUserEmail(email);
            log.warn("이메일 중복 확인 {} : ", isTrue);
            return isTrue;
        }
        else {
            return false;
        }
    }

    // 닉네임 중복 체크
    public boolean isNickName(String nickName) {
        System.out.println("닉네임 : " + nickName);
        boolean isTrue = userRepository.existsByUserNickname(nickName);
        log.warn("닉네임 중복 확인 {} : ", isTrue);
        return isTrue;
    }


}
