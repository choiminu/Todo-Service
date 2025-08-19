package com.todo.auth.domain;

import static com.todo.common.exception.ErrorCode.UNSUPPORTED_LOGIN_PROVIDER;

import com.todo.auth.exception.AuthenticationException;

public enum LoginProvider {
    SERVER, GOOGLE, KAKAO

    ;

    public static LoginProvider from(String provider) {
        for (LoginProvider value : LoginProvider.values()) {
            if (value.name().equalsIgnoreCase(provider)) {
                return value;
            }
        }
        throw new AuthenticationException(UNSUPPORTED_LOGIN_PROVIDER);
    }

}
