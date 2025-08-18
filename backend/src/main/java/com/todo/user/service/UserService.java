package com.todo.user.service;

import com.todo.user.domain.User;
import com.todo.user.domain.repository.UserRepository;
import com.todo.user.dto.SignupRequest;
import com.todo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long signup(SignupRequest request) {
        validateDuplicateEmail(request.getEmail());
        User user = userMapper.signupRequestToEntity(request);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }
    }


}
