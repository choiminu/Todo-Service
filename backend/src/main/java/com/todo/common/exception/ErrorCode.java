package com.todo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 사용자 관련 에러
     */
    EMAIL_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "U01", "이미 사용중인 이메일입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
