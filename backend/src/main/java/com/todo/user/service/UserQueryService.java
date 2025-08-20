package com.todo.user.service;

import static com.todo.common.exception.ErrorCode.EMAIL_NOT_UNIQUE;
import static com.todo.common.exception.ErrorCode.PASSWORD_MISMATCH;
import static com.todo.common.exception.ErrorCode.USER_NOT_FOUND;

import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.dto.SignupRequest;
import com.todo.user.exception.UserException;
import com.todo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository
                .findUserById(id)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    public User findUserByEmail(String email) {
        return userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }


}
