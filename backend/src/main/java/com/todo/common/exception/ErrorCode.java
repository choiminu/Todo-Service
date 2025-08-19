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
    EMAIL_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "U01", "이미 사용중인 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "U02", "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U03", "해당 사용자를 찾을 수 없습니다."),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "U04", "이메일 또는 아이디가 일치하지 않습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
