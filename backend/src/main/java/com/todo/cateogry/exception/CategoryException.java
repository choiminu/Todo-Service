package com.todo.cateogry.exception;

import com.todo.common.exception.CustomException;
import com.todo.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CategoryException extends CustomException {
    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public CategoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CategoryException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
