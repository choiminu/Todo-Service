package com.todo.user.presentation;

import com.todo.common.response.SuccessResponse;
import com.todo.user.dto.SignupRequest;
import com.todo.user.service.UserService;
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

    private final UserService userService;

    @PostMapping
    public SuccessResponse<Long> signup(@RequestBody SignupRequest request) {
        return SuccessResponse.success(HttpStatus.CREATED, userService.signup(request));
    }

}
