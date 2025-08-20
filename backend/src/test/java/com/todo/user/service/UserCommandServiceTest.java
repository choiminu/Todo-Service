package com.todo.user.service;

import static com.todo.common.exception.ErrorCode.EMAIL_NOT_UNIQUE;
import static com.todo.common.exception.ErrorCode.PASSWORD_MISMATCH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.user.application.service.UserCommandService;
import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.application.dto.SignupRequest;
import com.todo.user.exception.UserException;
import com.todo.user.application.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserCommandService userCommandService;

    String email = "tester@gmail.com";
    String password = "helloWorld";
    String confirmPassword = "helloWorld";

    User user = User.builder()
            .id(1L)
            .email(email)
            .password(password)
            .build();

    @Test
    @DisplayName("이메일과 비밀번호를 기반으로 회원가입을 할 수 있다.")
    void 회원가입_성공() {
        //given
        SignupRequest req = new SignupRequest(email, password, confirmPassword);

        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.signupRequestToEntity(any())).thenReturn(user);

        //when
        Long userId = userCommandService.signup(req);

        //then
        Assertions.assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("회원가입시 패스워드는 암호화되어 저장된다.")
    void 패스워드_암호화() {
        //given
        SignupRequest req = new SignupRequest(email, password, confirmPassword);

        when(userMapper.signupRequestToEntity(any())).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        //when
        userCommandService.signup(req);

        //then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");

    }

    @Test
    @DisplayName("이미 가입된 이메일로 회원가입을 하는 경우 예외가 발생한다.")
    void 회원가입_실패_이메일_중복() {
        //given
        SignupRequest req = new SignupRequest(email, password, confirmPassword);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        //when & then
        Assertions.assertThatThrownBy(() -> userCommandService.signup(req))
                .isInstanceOf(UserException.class)
                .hasMessage(EMAIL_NOT_UNIQUE.getMessage());
    }

    @Test
    @DisplayName("회원가입 과정에서 패스워드와 패스워드 확인 값이 다르면 예외가 발생한다.")
    void 회원가입_실패_패스워드_불일치() {
        //given
        SignupRequest req = new SignupRequest(email, password, "wrongPassword");

        //when & then
        Assertions.assertThatThrownBy(() -> userCommandService.signup(req))
                .isInstanceOf(UserException.class)
                .hasMessage(PASSWORD_MISMATCH.getMessage());
    }

}