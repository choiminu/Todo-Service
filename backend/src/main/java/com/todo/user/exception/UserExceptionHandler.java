package com.todo.user.exception;

import com.todo.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> userExceptionHandler(UserException e) {
        log.error("📌사용자 예외 발생", e);
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.error(e.getErrorCode()));
    }
}
