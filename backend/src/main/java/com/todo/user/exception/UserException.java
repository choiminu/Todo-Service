package com.todo.user.exception;

import com.todo.common.exception.CustomException;
import com.todo.common.exception.ErrorCode;

public class UserException extends CustomException {
    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UserException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
