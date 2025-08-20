package com.todo.user.application.mapper;

import com.todo.user.domain.User;
import com.todo.user.application.dto.SignupRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User signupRequestToEntity(SignupRequest request);
}
