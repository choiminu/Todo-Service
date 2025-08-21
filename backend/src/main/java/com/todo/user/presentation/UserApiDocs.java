package com.todo.user.presentation;

import com.todo.common.response.ErrorResponse;
import com.todo.common.response.SuccessResponse;
import com.todo.user.application.dto.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserApiDocs {
    @Operation(summary = "회원가입 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(
                                    name = "이메일 중복",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"U01\",\n"
                                            + "  \"message\": \"이미 사용중인 이메일입니다.\"\n"
                                            + "}"
                            ), @ExampleObject(
                                    name = "패스워드 불일치",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"U02\",\n"
                                            + "  \"message\": \"비밀번호가 일치하지 않습니다.\"\n"
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
                            examples = @ExampleObject(
                                    name = "서버 내부 오류",
                                    value = "{\n"
                                            + "  \"status\": 500,\n"
                                            + "  \"code\": \"S01\",\n"
                                            + "  \"message\": \"서버 내부 오류가 발생했습니다.\"\n"
                                            + "}"
                            )
                    )
            )
    })
    SuccessResponse<Long> signup(@RequestBody(
            description = "회원가입 정보",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SignupRequest.class),
                    examples = @ExampleObject(
                            "{\n"
                                    + "  \"email\": \"user@gmail.com\",\n"
                                    + "  \"password\": \"password!@#\",\n"
                                    + "  \"confirmPassword\": \"password!@#\"\n"
                                    + "}"
                    )
            )
    ) SignupRequest request);
}
