package com.projectBackend.project.controller;

import com.projectBackend.project.entity.Member;
import com.projectBackend.project.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;

    // 로그인 상태 체크 (+ refresh 토큰 유효성 체크)
    @GetMapping("/isLogin")
    public ResponseEntity<Boolean> isLogin(@RequestParam String accessToken) {
        boolean isTrue = authService.isLogined(accessToken);
        return ResponseEntity.ok(isTrue);
    }

    // 어드민 체크
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestParam String accessToken) {
        boolean isTrue = authService.isAdmin(accessToken);
        return ResponseEntity.ok(isTrue);
    }






}
