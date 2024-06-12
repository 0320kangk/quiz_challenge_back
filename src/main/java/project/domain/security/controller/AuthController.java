package project.domain.security.controller;

import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.domain.member.dto.LoginDto;
import project.domain.security.jwt.JwtFilter;
import project.domain.security.jwt.TokenProvider;
import project.domain.security.jwt.dto.RefreshTokenDto;
import project.domain.security.jwt.dto.TokenDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> authorize(@Validated @RequestBody LoginDto loginDto) {
        log.info("/authenticate TEST");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String accessToken = tokenProvider.createAccessToken(authentication);
        log.info("createTokenProvider start");
        String refreshToken = tokenProvider.createRefreshToken(authentication);
        log.info("createTokenProvider end");

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        //tokenDto 를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
    }
    @PostMapping("/auth/refresh")
    public ResponseEntity<TokenDto> refreshToken(@Validated @RequestBody RefreshTokenDto refreshTokenDto) throws AuthenticationException {
             Authentication authentication = tokenProvider.getAuthentication(refreshTokenDto.getRefreshToken());
            String accessToken = tokenProvider.createAccessToken(authentication);
            String newRefreshToken = tokenProvider.createRefreshToken(authentication);
            log.info("status test");
            return new ResponseEntity<>(new TokenDto(accessToken, newRefreshToken), HttpStatus.OK);

    }
    @PostMapping("/auth")
    public ResponseEntity<Void> authCheck() throws AuthenticationException {

        return ResponseEntity.ok().build();
    }
}