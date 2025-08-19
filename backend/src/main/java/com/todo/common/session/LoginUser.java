package com.todo.common.session;

import com.todo.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser {
    private Long userId;
    private UserRole role;
}
