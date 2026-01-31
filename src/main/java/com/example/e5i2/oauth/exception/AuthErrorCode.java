package com.example.e5i2.oauth.exception;


import com.example.e5i2.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {
    INVALID_PASSWORD_AUTHORIZATION(401, "AUTH_002", "비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN_SIGNATURE(401, "AUTH_003", "잘못된 토큰 서명입니다."),
    EXPIRED_TOKEN(401, "AUTH_004", "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "AUTH_005", "지원되지 않는 토큰입니다."),
    INVALID_TOKEN_FORM(401, "AUTH_006", "토큰이 형식이 유효하지 않습니다."),
    LOGGED_OUT_TOKEN(401, "AUTH_007", "블랙리스트에 등록된 토큰입니다."),
    EMAIL_ALREADY_VERIFIED(401, "AUTH_008", "이미 인증된 이메일입니다."),
    EMAIL_CODE_SEND_FAIL(503, "AUTH_009", "이메일 인증코드 발송에 실패했습니다."),
    EMAIL_CODE_NOT_EXIST(401, "AUTH_010", "이메일 인증코드 발송을 먼저 진행 해주세요."),
    INVALID_EMAIL_CODE_AUTHORIZATION(401, "AUTH_011", "이메일 인증코드가 일치하지 않습니다."),
    TOO_MANY_REQUESTS(429, "AUTH_012", "인증 코드는 60초당 한 번만 요청할 수 있습니다."),
    EMAIL_VERIFY_REQUIRED(401, "AUTH_013", "이메일 인증을 진행해주세요."),
    ;

    private final int status;
    private final String code;
    private final String message;

    AuthErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
