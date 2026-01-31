package com.example.e5i2.oauth.exception;

import com.example.e5i2.global.exception.CustomException;
import com.example.e5i2.global.exception.ErrorCode;

public class AuthException extends CustomException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
