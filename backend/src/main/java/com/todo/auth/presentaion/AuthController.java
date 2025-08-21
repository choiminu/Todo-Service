package com.todo.auth.presentaion;

import com.todo.common.session.LoginUser;
import com.todo.auth.application.dto.LoginRequest;
import com.todo.auth.application.service.AuthService;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs{

    private final AuthService authService;
    private final SessionManager sessionManager;

    @PostMapping("/login/{provider}")
    public SuccessResponse<LoginUser> login(
            @PathVariable("provider") String provider,
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
    ) {
        LoginUser session = authService.login(provider, loginRequest);
        sessionManager.create(request, session);
        return SuccessResponse.success(HttpStatus.OK, session);
    }

}
