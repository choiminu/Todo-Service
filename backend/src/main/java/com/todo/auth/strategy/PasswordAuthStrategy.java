package com.todo.auth.strategy;

import static com.todo.common.exception.ErrorCode.LOGIN_FAIL;

import com.todo.auth.domain.LoginProvider;
import com.todo.auth.domain.LoginUser;
import com.todo.auth.dto.LoginRequest;
import com.todo.user.domain.User;
import com.todo.user.exception.UserException;
import com.todo.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordAuthStrategy implements AuthStrategy{

    private final UserDomainService userDomainService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(LoginProvider provider) {
        return provider == LoginProvider.SERVER;
    }

    @Override
    public LoginUser authentication(LoginRequest request) {
        final String email = request.getEmail();
        final String password = request.getPassword();

        User findUser = userDomainService.findUserByEmail(email);
        if (!(passwordEncoder.matches(password, findUser.getPassword()))) {
            throw new UserException(LOGIN_FAIL);
        }

        return new LoginUser(findUser.getId());
    }
}
