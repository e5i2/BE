package com.example.e5i2.oauth.controller;

import com.example.e5i2.oauth.data.response.RedirectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e5i2.oauth.data.request.IdTokenRequest;
import com.example.e5i2.oauth.data.request.SignupRequest;
import com.example.e5i2.oauth.data.response.LoginResponse;
import com.example.e5i2.oauth.data.response.SignupResponse;
import com.example.e5i2.oauth.service.OauthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/oauth2")
public class OauthController {
    private final OauthService oauthService;

    @PostMapping("/google")
    public ResponseEntity<Object> googleCallback(@RequestBody IdTokenRequest idTokenRequest) {
        Object result = oauthService.loginWithGoogle(idTokenRequest);

        if (result instanceof LoginResponse loginResponse) {
            return ResponseEntity.ok(loginResponse);
        } else if (result instanceof String email) {
            return ResponseEntity.ok(new RedirectResponse(email, false));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = oauthService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
    }
}
