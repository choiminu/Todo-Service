package com.todo.auth.strategy;

import com.todo.auth.domain.LoginProvider;
import com.todo.common.session.LoginUser;
import com.todo.auth.dto.LoginRequest;

public interface AuthStrategy {
    boolean supports(LoginProvider provider);
    LoginUser authentication(LoginRequest request);
}
