package org.example.auth.blueprint.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.model.DataMap;
import org.example.auth.blueprint.security.dto.AccountDTO;
import org.example.auth.blueprint.security.filter.JwtTokenFilter;
import org.example.auth.blueprint.security.service.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //사용자 인증으로 토큰 발급
    @PostMapping("/token")
    public ResponseEntity<?> authorize(@Valid @RequestBody AccountDTO.Login dto) {
        DataMap token = accountService.authenticate(dto.getUserId(), dto.getUserPassword());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtTokenFilter.AUTHORIZATION_HEADER, "Bearer " + token.get("accessToken"));
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }

    //리프레시 토큰을 활용한 액세스 토큰 갱신
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody AccountDTO.RefreshToken dto) {
        DataMap token = accountService.refreshToken(dto.getRefreshToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtTokenFilter.AUTHORIZATION_HEADER, "Bearer " + token.get("accessToken"));
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }
}
