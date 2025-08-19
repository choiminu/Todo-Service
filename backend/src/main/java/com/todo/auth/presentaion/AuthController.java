package com.todo.auth.presentaion;

import com.todo.auth.domain.LoginUser;
import com.todo.auth.dto.LoginRequest;
import com.todo.auth.service.AuthService;
import com.todo.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public SuccessResponse<LoginUser> login(@RequestParam(required = false) String provider, @RequestBody LoginRequest request){
        return SuccessResponse.success(HttpStatus.OK, authService.login(provider, request));
    }

}
