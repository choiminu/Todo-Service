package com.todo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 서버 내부 에러
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S01", "서버 내부 오류가 발생했습니다."),
    /**
     * 인증 관련 에러
     */
    UNSUPPORTED_LOGIN_PROVIDER(HttpStatus.BAD_REQUEST, "A01", "지원하지 않는 로그인 제공자입니다."),

    /**
     * 인가 관련 에러
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U01", "로그인이 필요한 서비스입니다."),
    FORBIDDEN(HttpStatus.UNAUTHORIZED, "U02", "접근 권한이 부족합니다."),

    /**
     * 카테고리 관련 에러
     */
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C01", "카테고리를 찾을 수 없습니다."),
    CATEGORY_UPDATE_FORBIDDEN(HttpStatus.BAD_REQUEST, "C02", "본인의 카테고리만 수정할 수 있습니다."),

    /**
     * Task 관련 에러
     */
    TASK_NOT_FOUND(HttpStatus.BAD_REQUEST, "T01", "해당 Task를 찾을 수 없습니다."),
    TASK_ACCESS_FORBIDDEN(HttpStatus.BAD_REQUEST, "T02", "해당 Task의 접근 권한이 없습니다."),
    TASK_PERIOD_INVALID(HttpStatus.BAD_REQUEST, "T03", "유효하지 않은 기간입니다."),



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
