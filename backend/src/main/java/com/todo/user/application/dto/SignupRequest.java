package com.todo.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {
    @Schema(example = "user@gmail.com")
    private String email;

    @Schema(example = "password!@#")
    private String password;

    @Schema(example = "password!@#")
    private String confirmPassword;
}
