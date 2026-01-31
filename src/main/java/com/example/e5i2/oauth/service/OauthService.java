package com.example.e5i2.oauth.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e5i2.global.properties.GoogleOauthProperties;
import com.example.e5i2.oauth.data.request.IdTokenRequest;
import com.example.e5i2.oauth.data.request.SignupRequest;
import com.example.e5i2.oauth.data.response.LoginResponse;
import com.example.e5i2.oauth.data.response.SignupResponse;
import com.example.e5i2.oauth.data.response.TokenResponse;
import com.example.e5i2.oauth.exception.AuthErrorCode;
import com.example.e5i2.oauth.exception.AuthException;
import com.example.e5i2.oauth.utils.TokenProvider;
import com.example.e5i2.user.domain.User;
import com.example.e5i2.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {
    private final GoogleOauthProperties googleOauthProperties;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public Object loginWithGoogle(IdTokenRequest idTokenRequest) {
        String email = verifyAndGetEmail(idTokenRequest.idToken());

        Optional<User> userOpt =
                userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            TokenResponse tokenResponse = getTokenResponse(user.getId());

            return LoginResponse.builder()
                    .memberId(user.getId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .tokenResponse(tokenResponse)
                    .build();
        } else {
            return email;
        }
    }

    /** 구글 ID Token 검증 로직 */
    private String verifyAndGetEmail(String idTokenString) {
        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList(googleOauthProperties.clientId()))
                        .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                return payload.getEmail();
            } else {
                log.error("유효하지 않은 구글 ID 토큰입니다.");
                throw new AuthException(AuthErrorCode.INVALID_TOKEN);
            }
        } catch (Exception e) {
            log.error("ID 토큰 검증 중 에러 발생: {}", e.getMessage());
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        userRepository.existsByEmail(signupRequest.email());
        userRepository.existsByNickname(signupRequest.nickname());

        User user = userRepository.save(
            User.createUser(
                signupRequest.email(),
                signupRequest.nickname(),
                signupRequest.gender(),
                signupRequest.height(),
                signupRequest.weight()));

        TokenResponse tokenResponse = getTokenResponse(user.getId());
        log.info("✅ 회원 가입이 정상적으로 처리되었습니다. 회원 ID: {}", user.getId());
        return SignupResponse.builder()
                .email(signupRequest.email())
                .nickname(signupRequest.nickname())
                .gender(signupRequest.gender())
                .height(signupRequest.height())
                .weight(signupRequest.weight())
                .tokenResponse(tokenResponse)
                .build();
    }

    public TokenResponse getTokenResponse(Long memberId) {
		return tokenProvider.generateToken(memberId);
    }
}
