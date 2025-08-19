package com.todo.task.exception;

import com.todo.common.exception.CustomException;
import com.todo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TaskException extends CustomException {
    public TaskException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public TaskException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public TaskException(ErrorCode errorCode) {
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
