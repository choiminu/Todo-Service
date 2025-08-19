package com.todo.auth.strategy;

import com.todo.auth.domain.LoginProvider;
import com.todo.auth.dto.LoginRequest;

public interface AuthStrategy {
    boolean supports(LoginProvider provider);
    Object authentication(LoginRequest request);
}
