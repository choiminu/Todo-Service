package com.todo.user.service;

import static com.todo.common.exception.ErrorCode.EMAIL_NOT_UNIQUE;
import static com.todo.common.exception.ErrorCode.PASSWORD_MISMATCH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.dto.SignupRequest;
import com.todo.user.exception.UserException;
import com.todo.user.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserQueryService userQueryService;

    User user;
    String email;
    String password;
    String confirmPassword;

    @BeforeEach
    void beforeEach() {
        this.email = "user@gmail.com";
        this.password = "user1234";
        this.confirmPassword = "user1234";

        user = User.builder()
                .id(1L)
                .email(email)
                .password(password)
                .build();
    }


}