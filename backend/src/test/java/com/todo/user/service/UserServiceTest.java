package com.todo.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.dto.SignupRequest;
import com.todo.user.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

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

    @Test
    @DisplayName("이메일과 비밀번호를 기반으로 회원가입을 할 수 있다.")
    void 회원가입_성공() {
        //given
        SignupRequest req = new SignupRequest(email, password, confirmPassword);

        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.signupRequestToEntity(any())).thenReturn(user);

        //when
        Long userId = userService.signup(req);

        //then
        Assertions.assertThat(userId).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 가입된 이메일로 회원가입을 하는 경우 예외가 발생한다.")
    void 회원가입_실패_이메일_중복() {
        //given
        SignupRequest req = new SignupRequest(email, password, confirmPassword);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        //when & then
        Assertions.assertThatThrownBy(() -> userService.signup(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 사용중인 이메일입니다.");
    }

}