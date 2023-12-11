package com.projectBackend.project.controller;

import com.projectBackend.project.dto.TokenDto;
import com.projectBackend.project.dto.UserReqDto;
import com.projectBackend.project.dto.UserResDto;
import com.projectBackend.project.jwt.TokenProvider;
import com.projectBackend.project.service.AuthService;
import com.projectBackend.project.service.MailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.projectBackend.project.service.MailService.EPW;
import static com.projectBackend.project.utils.Common.*;

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
    private final MailService mailService;


    // 회원가입
    @PostMapping("/sign")
    public ResponseEntity<UserResDto> sign(@RequestBody UserReqDto memberReqDto) {
        log.warn("memberReqDto {} :", memberReqDto);
        UserResDto result = authService.signup(memberReqDto);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    // 회원 가입 시 이메일 인증 + 중복 체크
    @PostMapping("/mailConfirm")
    public ResponseEntity<Boolean> mailConfirm(@RequestParam String email) throws Exception {
        boolean isTrue = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 T or F : " + isTrue);
        return ResponseEntity.ok(isTrue);
    }

    // 인증 번호 체크, post 방식만 허용하는 듯.
    @GetMapping("/ePw")
    public ResponseEntity<Boolean> checkEpw(@RequestParam String epw) {
        System.out.println("프론트에서 날린 epw" + epw);
        if (epw.equals(EPW)) {
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.ok(false);
        }
    }

    // 닉네임 중복 체크
    @GetMapping("/nickName")
    public ResponseEntity<Boolean> nickName(@RequestParam String nickName) {
        return ResponseEntity.ok(authService.isNickName(nickName));
    }


    // 카카오 로그인 및 토큰 발급
    @GetMapping("/kakao")
    public ResponseEntity<Map<String, Object>> kakao(@RequestParam String code) {
        System.out.println(" code : " + code);
        System.out.println(" clientId : " + clientId);
        System.out.println(" redirectUri : " + redirectUri);
        System.out.println(" clientSecret : " + clientSecret);
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP 요청 바디에 담을 데이터 설정 (grant_type, client_id, redirect_uri, code, client_secret)
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("client_id", clientId);
        requestBody.put("redirect_uri", redirectUri);
        requestBody.put("code", code);
        requestBody.put("client_secret", clientSecret);

        // URL 빌더를 사용하여 요청 URL 생성
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(kakaoTokenUrl);

        // RestTemplate을 사용하여 POST 요청을 보내고 응답을 받아옴
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(builder.toUriString(), requestBody, Map.class);

        // 받아온 응답을 그대로 클라이언트에게 전달 (상태 코드, 응답 데이터)
        return ResponseEntity.ok(response.getBody());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserReqDto memberReqDto) {
        return ResponseEntity.ok(authService.login(memberReqDto));
    }





   


}
