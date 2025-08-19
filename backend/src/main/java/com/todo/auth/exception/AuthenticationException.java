package com.todo.auth.exception;

import com.todo.common.exception.CustomException;
import com.todo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AuthenticationException extends CustomException {
    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuthenticationException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
