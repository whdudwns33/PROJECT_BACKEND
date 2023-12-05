package com.projectBackend.project.jwt;



import com.projectBackend.project.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY ="auth";
    private static final String BEARER_TYPE = "Bearer"; // 토큰의 타입
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30000; //
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 24시간
    private final Key key; // 토큰을 서명(signiture)하기 위한 Key


    //
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // HS512 알고리즘을 사용하는 키 생성
    }

    // 토큰 생성
    public TokenDto generateTokenDto(Authentication authentication) {
        log.warn("authentication {} : ", authentication);
        // 권한 정보 문자열 생성,
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime(); // 현재 시간
        // 토큰 만료 시간 설정
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // 토큰 생성
        String accessToken = io.jsonwebtoken.Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
//                    .claim("email", member.getEmail()) // member가 인스턴스 변수인 경우
//                    .claim("name", member.getName()) // member가 인스턴스 변수인 경우
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // 리프레시 토큰 생성
        String refreshToken = io.jsonwebtoken.Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .setSubject(authentication.getName())
//                    .claim("email", member.getEmail()) // member가 인스턴스 변수인 경우
//                    .claim("name", member.getName()) // member가 인스턴스 변수인 경우
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        log.warn("accessToken {} : ", accessToken);
        log.warn("refreshToken {} : ", refreshToken);
        // 토큰 정보를 담은 TokenDto 객체 생성
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
    }

    // 새로운 accessToken
    public String generateNewAccessToken(String Token) {
        // refresh 토큰을 파싱하여 클레임을 얻습니다.
        Claims refreshTokenClaims = parseClaims(Token);
        // refresh 토큰이 유효한지 확인합니다.
        if (validateToken(Token) && refreshTokenClaims.get(AUTHORITIES_KEY) != null) {
            // refresh 토큰 클레임에서 사용자 정보를 추출합니다.
            // Long id 값
            String subject = refreshTokenClaims.getSubject();
            // auth 값
            String authorities = refreshTokenClaims.get(AUTHORITIES_KEY).toString();

            // 권한을 GrantedAuthority 객체의 리스트로 변환합니다.
            Collection<? extends GrantedAuthority> grantedAuthorities =
                    Arrays.stream(authorities.split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            // 추출된 정보를 사용하여 UserDetails 객체를 생성합니다.
            User principal = new User(subject, "", grantedAuthorities);

            // 추출된 정보를 사용하여 새로운 access 토큰을 생성합니다.
            String newAccessToken = io.jsonwebtoken.Jwts.builder()
                    .setSubject(subject)
                    .claim(AUTHORITIES_KEY, authorities)
//                    .claim("email", member.getEmail()) // member가 인스턴스 변수인 경우
//                    .claim("name", member.getName()) // member가 인스턴스 변수인 경우
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            // principal 로깅 (선택 사항)
            log.warn("principal {} :", principal);

            // 새로운 access 토큰을 반환합니다.
            return newAccessToken;
        } else {
            // 유효하지 않은 refresh 토큰 처리 (필요에 따라 예외를 throw하거나 null을 반환합니다.)
            throw new RuntimeException("유효하지 않은 refresh 토큰");
        }
    }

    // 토큰 복호화
    private Claims parseClaims(String accessToken) {
        try {
            return io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        // 토큰의 claim 부분에서 권한 정보를 체크. 만약 권한이 없다면 null 반환
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰");
        }
        // UserDetails 객체를 생성할 때 사용되며, 사용자의 권한에 따라 인증 및 권한 부여를 수행하는 데 활용.
        // 토큰에 담긴 권한 정보들을 가져옴
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // 권한 정보들을 이용해 유저 객체를 만들어서 반환, 여기서 User 객체는 UserDetails 인터페이스를 구현한 객체
        User principal = new User(claims.getSubject(), "", authorities);
        log.warn("principal {} :", principal);
        // 유저 객체, 토큰, 권한 정보들을 이용해 인증 객체를 생성해서 반환
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    // 토큰의 유효성 검사
    public boolean validateToken(String token) {
        try {
            io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | io.jsonwebtoken.MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
