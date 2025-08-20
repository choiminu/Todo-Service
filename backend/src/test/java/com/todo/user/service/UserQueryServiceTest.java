package com.todo.user.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.todo.common.exception.ErrorCode;
import com.todo.user.application.service.UserQueryService;
import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.exception.UserException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserQueryService userQueryService;

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @DisplayName("ID로 사용자 조회에 성공한다")
    void findUserById_success(Long userId) {
        // given
        User user = User.builder()
                .id(userId)
                .email("test" + userId + "@gmail.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        // when
        User findUser = userQueryService.findUserById(userId);

        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getId()).isEqualTo(userId);
        assertThat(findUser.getEmail()).isEqualTo("test" + userId + "@gmail.com");
    }

    @ParameterizedTest
    @ValueSource(longs = {99L, 123L})
    @DisplayName("ID로 사용자 조회 시 존재하지 않으면 예외가 발생한다")
    void findUserById_fail(Long userId) {
        // given
        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> userQueryService.findUserById(userId))
                .isInstanceOf(UserException.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1, test@gmail.com",
            "2, hello@gmail.com"
    })
    @DisplayName("이메일로 사용자 조회에 성공한다")
    void findUserByEmail_success(Long userId, String email) {
        // given
        User user = User.builder()
                .id(userId)
                .email(email)
                .password("encodedPassword")
                .build();

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // when
        User findUser = userQueryService.findUserByEmail(email);

        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getId()).isEqualTo(userId);
        assertThat(findUser.getEmail()).isEqualTo(email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin@yahoo.com", "not@@@@"})
    @DisplayName("이메일로 사용자 조회 시 존재하지 않으면 예외가 발생한다")
    void findUserByEmail_fail(String email) {
        // given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> userQueryService.findUserByEmail(email))
                .isInstanceOf(UserException.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }



}