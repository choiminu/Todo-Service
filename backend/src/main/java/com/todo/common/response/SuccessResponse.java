package com.todo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private final T data;
    private final int status;
    private final String message;

    public static <T> SuccessResponse<T> success(HttpStatus httpStatus, T data) {
        return new SuccessResponse<>(data, httpStatus.value(), httpStatus.name());
    }

    public static SuccessResponse<Void> success(final HttpStatus httpStatus) {
        return success(httpStatus, null);
    }
}
