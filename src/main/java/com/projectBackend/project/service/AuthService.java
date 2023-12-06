package com.projectBackend.project.service;


import com.projectBackend.project.dto.MemberReqDto;
import com.projectBackend.project.dto.MemberResDto;
import com.projectBackend.project.dto.TokenDto;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.jwt.TokenProvider;
import com.projectBackend.project.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원 가입
    public MemberResDto signup(MemberReqDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toEntity(passwordEncoder);
        return MemberResDto.of(memberRepository.save(member));
    }
    
    // 로그인
    public TokenDto login(MemberReqDto requestDto) {
        System.out.println("requestDto 이메일 :" + requestDto.getEmail());
        System.out.println("requestDto 패스워드 :" + requestDto.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        log.warn("authenticationToken: {}", authenticationToken);

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        log.warn("authentication: {}", authentication);

        return tokenProvider.generateTokenDto(authentication);
    }
}
