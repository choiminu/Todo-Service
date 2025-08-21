package com.todo.auth.presentaion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.common.session.LoginUser;
import com.todo.auth.application.dto.LoginRequest;
import com.todo.auth.application.service.AuthService;
import com.todo.common.session.SessionManager;
import com.todo.user.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    SessionManager sessionManager;

    @MockitoBean
    AuthService authService;

    @Test
    void 로그인_성공시_세션정보를_반환한다() throws Exception {
        //given
        LoginRequest req = new LoginRequest("user@gmail.com", "rawPassword");

        doNothing().when(sessionManager).create(any(), any());
        when(authService.login(null, req)).thenReturn(new LoginUser(1L, UserRole.USER));

        //when & then
        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.status").value(200));
    }

}