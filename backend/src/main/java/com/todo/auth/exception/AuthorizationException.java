package com.todo.auth.exception;

import com.todo.common.exception.CustomException;
import com.todo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AuthorizationException extends CustomException {
    public AuthorizationException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public AuthorizationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
