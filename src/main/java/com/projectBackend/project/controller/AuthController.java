package com.projectBackend.project.controller;

import com.projectBackend.project.dto.MemberReqDto;
import com.projectBackend.project.dto.MemberResDto;
import com.projectBackend.project.dto.TokenDto;
import com.projectBackend.project.jwt.TokenProvider;
import com.projectBackend.project.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/sign")
    public ResponseEntity<MemberResDto> sign(@RequestBody MemberReqDto memberReqDto) {
        log.warn("memberReqDto {} :", memberReqDto);
        MemberResDto result = authService.signup(memberReqDto);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(authService.login(memberReqDto));
    }


   


}
