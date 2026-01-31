package com.example.e5i2.user.exception;


import com.example.e5i2.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(400, "USER_001", "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(409, "USER_002", "이미 가입된 이메일입니다."),
    DUPLICATE_NICKNAME(409, "USER_003", "이미 존재하는 닉네임입니다.");

    private final int status;
    private final String code;
    private final String message;

    UserErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
