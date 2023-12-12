package com.projectBackend.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.projectBackend.project.entity.Token;
//import com.projectBackend.project.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.projectBackend.project.utils.Common.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoService {
//    private final TokenRepository tokenRepository;
//    private final TokenDto tokenDto;

    // 카카오 로그인 토큰 발급
    public String kakaoToken (String code) {
        try {
            //카카오로 post 방식 요청
            RestTemplate rt = new RestTemplate();

            //HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            //HttpBody 오브젝트 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("client_secret", clientSecret);
            params.add("code", code);

            //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                    new HttpEntity<>(params, headers);

            //Http 요청하기 - Post방식으로 그리고 response 변수의 응답받음.
            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
            log.info("response {} : ", response.getBody());
            System.out.println("status" + response.getStatusCodeValue());
            if (response.getStatusCodeValue() == 200) {
                // 카카오 토큰 정보를 저장하기 위한 객체 생성
//                Token token = new Token();
                ObjectMapper objectMapper = new ObjectMapper();

                // JSON 문자열을 JsonNode로 변환 및 토큰 정보 출력
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                String accessToken = jsonNode.get("access_token").asText();
//                String tokenType = jsonNode.get("token_type").asText();
//                String refreshToken = jsonNode.get("refresh_token").asText();
//                Long accessExpire = Long.valueOf(jsonNode.get("expires_in").asText());
//                Long refreshExpire = Long.valueOf(jsonNode.get("refresh_token_expires_in").asText());

                log.info("Access Token: {}", accessToken);
//                log.info("Token Type: {}", tokenType);
//                log.info("Refresh Token: {}", refreshToken);
                String kakaoEmailData = kakaoEmail(accessToken);
                log.info("kakao email : {}", kakaoEmailData);
                return kakaoEmailData;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("post 응답이 되지 않음");
            e.printStackTrace();
            return null;
        }
    }


    // 카카오 이메일 받아오기
    public String kakaoEmail (String accessToken) {
        try {
            RestTemplate rt = new RestTemplate();
            // 토큰 가져오기 => 수정 필요
            ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println("accessToken : " + accessToken);

            // HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            headers.set("Authorization", "Bearer " + accessToken);
//        System.out.println("headers : " + headers);

            // get 방식은 body가 필요 없지만 exchange를 쓰기 위해서는 필요
            HttpEntity<String> requestEntity = new HttpEntity<>("request body content", headers);

            // GET 요청하기 - response 변수의 응답받음.
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    requestEntity,
                    String.class,
                    headers
            );
            System.out.println("Get RESPONSE : " + response);
            if (response.getStatusCodeValue() == 200) {
                // json 문자열의 바디 부분을 객체화
                // email의 경우 kakao_acount 내부에 존재
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                JsonNode kakaoAccountNode = jsonNode.path("kakao_account");
                String email = kakaoAccountNode.path("email").asText();
                if (email != null) {
                    log.info("email123 {} :", email);
                    return email;
                }
                else {
                    log.warn("카카오 이메일이 전송되지 않음");
                    return null;
                }
            }
            else {
                System.out.println("카카오 유저 연결이 되지 않음");
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
