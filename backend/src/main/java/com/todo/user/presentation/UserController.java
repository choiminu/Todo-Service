package com.todo.user.presentation;

import static com.todo.common.response.SuccessResponse.success;

import com.todo.common.response.SuccessResponse;
import com.todo.user.application.dto.SignupRequest;
import com.todo.user.application.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserCommandService userCommandService;

    @Operation(summary = "회원가입", description = "신규 사용자를 생성하고 ID를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
    })
    @PostMapping
    public SuccessResponse<Long> signup(@RequestBody SignupRequest request) {
        return success(HttpStatus.CREATED, userCommandService.signup(request));
    }

}
