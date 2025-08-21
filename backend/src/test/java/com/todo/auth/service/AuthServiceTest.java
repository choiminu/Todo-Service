package com.todo.auth.service;

import static com.todo.common.exception.ErrorCode.LOGIN_FAIL;
import static com.todo.common.exception.ErrorCode.UNSUPPORTED_LOGIN_PROVIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.auth.application.service.AuthService;
import com.todo.auth.domain.LoginProvider;
import com.todo.common.session.LoginUser;
import com.todo.auth.application.dto.LoginRequest;
import com.todo.auth.exception.AuthenticationException;
import com.todo.auth.application.strategy.AuthStrategy;
import com.todo.user.domain.User;
import com.todo.user.domain.UserRole;
import com.todo.user.exception.UserException;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Spy
    List<AuthStrategy> authStrategies = new ArrayList<>();

    @Mock
    AuthStrategy passwordAuthStrategy;

    @InjectMocks
    AuthService authService;

    User user;
    String email;
    String password;

    @BeforeEach
    void beforeEach() {
        this.email = "user@gmail.com";
        this.password = "rawPassword";

        this.user = User.builder()
                .id(1L)
                .email(email)
                .password(password)
                .build();

        authStrategies.clear();
        authStrategies.add(passwordAuthStrategy);
    }

    @Test
    @DisplayName("Provider의 값이 null인 경우 기본으로 PasswordAuthStrategy가 선택되어 인증을 수행한다.")
    void 기본_서버_전략_선택() {
        //given
        LoginRequest request = new LoginRequest(email, password);

        when(passwordAuthStrategy.supports(LoginProvider.SERVER)).thenReturn(true);

        //when
        authService.login(null, request);

        //then
        verify(passwordAuthStrategy).authentication(request);
    }

    @Test
    @DisplayName("서버에서 지원하지 않은 전략인 경우 예외가 발생한다.")
    void 지원되지_않은_전략() {
        //given
        LoginRequest request = new LoginRequest(email, password);

        //when & then
        Assertions.assertThatThrownBy(() -> authService.login("not", request))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage(UNSUPPORTED_LOGIN_PROVIDER.getMessage());

    }

    @Test
    @DisplayName("로그인이 성공하면 사용자의 세션 정보를 반환한다.")
    void 로그인_성공() {
        //given
        LoginRequest request = new LoginRequest(email, password);

        LoginUser stub = new LoginUser(1L, UserRole.USER);

        when(passwordAuthStrategy.supports(LoginProvider.SERVER)).thenReturn(true);
        when(passwordAuthStrategy.authentication(any())).thenReturn(stub);

        //when
        LoginUser session = authService.login(null, request);

        //then
        assertThat(session.getUserId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이메일 또는 패스워드가 일치하지 않으면 예외가 발생한다.")
    void 로그인_실패_패스워드_이메일_불일치() {
        //given
        LoginRequest request = new LoginRequest(email, password);

        when(passwordAuthStrategy.supports(LoginProvider.SERVER)).thenReturn(true);
        when(passwordAuthStrategy.authentication(any())).thenThrow(new UserException(LOGIN_FAIL));

        //when & then
        Assertions.assertThatThrownBy(() -> authService.login(null, request))
                .isInstanceOf(UserException.class)
                .hasMessage(LOGIN_FAIL.getMessage());

    }

}