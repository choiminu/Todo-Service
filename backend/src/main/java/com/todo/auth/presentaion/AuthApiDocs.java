package com.todo.auth.presentaion;

import com.todo.auth.domain.LoginProvider;
import com.todo.auth.application.dto.LoginRequest;
import com.todo.common.response.ErrorResponse;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthApiDocs {

    @Operation(summary = "로그인 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "로그인 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(
                                    name = "패스워드 불일치",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"U04\",\n"
                                            + "  \"message\": \"이메일 또는 아이디가 일치하지 않습니다.\"\n"
                                            + "}"
                            ), @ExampleObject(
                                    name = "잘못된 소셜 로그인 제공자",
                                    value = "{\n"
                                            + "  \"status\": 500,\n"
                                            + "  \"code\": \"A01\",\n"
                                            + "  \"message\": \"지원하지 않는 로그인 제공자입니다.\"\n"
                                            + "}"
                            )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(
                                    name = "지원하지 않는 소셜 로그인 제공자",
                                    value = "{\n"
                                            + "  \"status\": 500,\n"
                                            + "  \"code\": \"A01\",\n"
                                            + "  \"message\": \"현재 해당 서비스의 로그인은 지원하지 않습니다.\"\n"
                                            + "}"
                            ), @ExampleObject(
                                    name = "서버 내부 오류",
                                    value = "{\n"
                                            + "  \"status\": 500,\n"
                                            + "  \"code\": \"S01\",\n"
                                            + "  \"message\": \"서버 내부 오류가 발생했습니다.\"\n"
                                            + "}"
                            )}
                    )
            )
    })
    SuccessResponse<LoginUser> login(
            @Parameter(
                    description = "로그인 제공자",
                    content = @Content(
                            schema = @Schema(implementation = LoginProvider.class),
                            examples = @ExampleObject(value = "SERVER")
                    )
            ) String provider,
            @RequestBody(
                    description = "로그인 정보",
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(value = "{\n"
                                    + "  \"email\": \"user@gmail.com\",\n"
                                    + "  \"password\": \"password!@#\"\n"
                                    + "}")
                    )
            ) LoginRequest loginRequest,
            HttpServletRequest request
    );
}
