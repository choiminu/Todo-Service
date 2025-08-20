package com.todo.auth.strategy;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.todo.auth.domain.LoginProvider;
import com.todo.common.session.LoginUser;
import com.todo.auth.dto.LoginRequest;
import com.todo.common.exception.ErrorCode;
import com.todo.user.domain.User;
import com.todo.user.exception.UserException;
import com.todo.user.service.UserCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class PasswordAuthStrategyTest {

    @Mock
    UserCommandService userCommandService;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PasswordAuthStrategy passwordAuthStrategy;

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
    }

    @Test
    @DisplayName("PasswordAuthStrategy는 SERVER 로그인 방식을 지원한다")
    void 서버_로그인_방식을_지원한다() {
        //given
        LoginProvider provider = LoginProvider.SERVER;

        //when
        boolean supports = passwordAuthStrategy.supports(provider);

        //then
        assertThat(supports).isTrue();
    }

    @Test
    @DisplayName("DB에 저장된 이메일과 비밀번호가 일치할 경우 로그인은 성공한다.")
    void 패스워드기반_로그인_성공() {
        //given
        LoginRequest request = new LoginRequest(email, password);

        when(userCommandService.findUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        //when
        LoginUser loginUser = passwordAuthStrategy.authentication(request);

        //then
        assertThat(loginUser.getUserId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("패스워드 불일치시에는 예외가 발생한다.")
    void 패스워드기반_로그인_실패_패스워드_불일치() {
        //given
        LoginRequest request = new LoginRequest(email, password);

        when(userCommandService.findUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        //when & then
        assertThatThrownBy(() -> passwordAuthStrategy.authentication(request))
                .isInstanceOf(UserException.class)
                .hasMessage(ErrorCode.LOGIN_FAIL.getMessage());
    }




}