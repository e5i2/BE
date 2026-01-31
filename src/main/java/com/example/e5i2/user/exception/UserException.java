package com.example.e5i2.user.exception;
import com.example.e5i2.global.exception.CustomException;
import com.example.e5i2.global.exception.ErrorCode;

public class UserException extends CustomException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
