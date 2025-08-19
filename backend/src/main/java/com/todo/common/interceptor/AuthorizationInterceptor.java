package com.todo.common.interceptor;

import static com.todo.common.exception.ErrorCode.FORBIDDEN;

import com.todo.auth.domain.Auth;
import com.todo.auth.exception.AuthorizationException;
import com.todo.common.session.LoginUser;
import com.todo.common.session.SessionManager;
import com.todo.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("📌AuthorizationInterceptor 동작");

        if (!(handler instanceof HandlerMethod hm)) {
            return true;
        }

        // 클래스와 메서드에 @Auth 애노테이션이 있는지 확인 없다면 보호 대상이 아니므로 통과
        Auth auth = resolveAuth(hm);
        if (auth == null){
            return true;
        }

        // roles(GUEST, USER, ADMIN)가 비었으면 로그인 여부만 확인
        LoginUser session = sessionManager.getUserSession(request);
        if (auth.roles().length == 0) {
            return true;
        }

        // roles(GUEST, USER, ADMIN)에서 지정한 Role 필요
        if (hasRequiredRole(auth.roles(), session.getRole())) {
            return true;
        }

        // 권한 부족
        throw new AuthorizationException(FORBIDDEN);
    }

    private Auth resolveAuth(HandlerMethod hm) {
        Auth method = hm.getMethodAnnotation(Auth.class);
        return (method != null) ? method : hm.getBeanType().getAnnotation(Auth.class);
    }

    private boolean hasRequiredRole(UserRole[] requiredRoles, UserRole actual) {
        for (UserRole role : requiredRoles) {
            if (role == actual) return true;
        }
        return false;
    }
}
