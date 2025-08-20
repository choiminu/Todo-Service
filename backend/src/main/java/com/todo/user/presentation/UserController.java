package com.todo.user.presentation;

import com.todo.common.response.SuccessResponse;
import com.todo.user.application.dto.SignupRequest;
import com.todo.user.application.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserCommandService userCommandService;

    @PostMapping
    public SuccessResponse<Long> signup(@RequestBody SignupRequest request) {
        return SuccessResponse.success(HttpStatus.CREATED, userCommandService.signup(request));
    }

}
