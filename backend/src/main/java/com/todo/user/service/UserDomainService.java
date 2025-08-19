package com.todo.user.service;

import com.todo.common.exception.ErrorCode;
import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository
                .findUserById(id)
                .orElseThrow(() -> new UserException(ErrorCode.EMAIL_NOT_UNIQUE));
    }
}
