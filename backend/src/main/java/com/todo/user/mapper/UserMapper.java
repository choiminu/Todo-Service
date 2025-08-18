package com.todo.user.mapper;

import com.todo.user.domain.User;
import com.todo.user.dto.SignupRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User signupRequestToEntity(SignupRequest request);
}
