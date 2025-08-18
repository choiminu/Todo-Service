package com.todo.common.response;

import com.todo.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponse error(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getErrorCode(), errorCode.getMessage());
    }
}
