package com.projectBackend.project.controller;

import com.projectBackend.project.entity.Member;
import com.projectBackend.project.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;

    // 로그인 상태 체크 (+ refresh 토큰 유효성 체크) -> 이메일 값 반환
    @GetMapping("/isLogin")
    public ResponseEntity<String> isLogin(@RequestParam String accessToken) {
        String email = authService.isLogined(accessToken);
        return ResponseEntity.ok(email);
    }

    // 어드민 체크
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestParam String accessToken) {
        boolean isTrue = authService.isAdmin(accessToken);
        return ResponseEntity.ok(isTrue);
    }

    // 길종환
    // 유저 포인트 충전
    @PostMapping("/increasePoints")
    public ResponseEntity<Boolean> increasePoints(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        int points = Integer.parseInt(payload.get("points"));
        boolean success = authService.increasePoints(email, points);

        if (success) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
    // 유저 포인트 환전
    @PostMapping("/exchangePoints")
    public ResponseEntity<Boolean> exchangePoints(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        int points = Integer.parseInt(payload.get("points"));
        boolean success = authService.decreasePoints(email, points);

        if (success) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }




}
