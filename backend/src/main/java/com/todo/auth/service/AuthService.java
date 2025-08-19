package com.todo.auth.service;

import com.todo.auth.domain.LoginProvider;
import com.todo.common.session.LoginUser;
import com.todo.auth.dto.LoginRequest;
import com.todo.auth.strategy.AuthStrategy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final List<AuthStrategy> authStrategies;

    public LoginUser login(String provider, LoginRequest request) {
        LoginProvider loginProvider = findAuthProvider(provider);

        return authStrategies.stream()
                .filter(a -> a.supports(loginProvider))
                .findFirst()
                .orElseThrow()
                .authentication(request);
    }

    private LoginProvider findAuthProvider(String provider) {
        if (provider == null) {
            return LoginProvider.SERVER;
        }
        return LoginProvider.from(provider);
    }


}
