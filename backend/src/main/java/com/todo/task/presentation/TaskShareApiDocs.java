package com.todo.task.presentation;

import com.todo.common.response.ErrorResponse;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import com.todo.task.application.dto.request.TaskShareRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.entity.vo.TaskShare;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task Share", description = "Task 공유 관련 API (공유 링크 생성 및 조회)")
public interface TaskShareApiDocs {

    @Operation(summary = "Task 공유 링크 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "공유 링크 생성 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "유효기간 오류",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"Z04\",\n"
                                                    + "  \"message\": \"유효기간 설정 잘못하였습니다.\"\n"
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Task 없음",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"T01\",\n"
                                                    + "  \"message\": \"해당 Task를 찾을 수 없습니다.\"\n"
                                                    + "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "접근권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "로그인 필요",
                                    value = "{\n"
                                            + "  \"status\": 401,\n"
                                            + "  \"code\": \"U01\",\n"
                                            + "  \"message\": \"로그인이 필요한 서비스입니다.\"\n"
                                            + "}"
                            )
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
    SuccessResponse<TaskShare> createShareLink(
            @Login LoginUser loginUser,
            @RequestBody(
                    description = "Task 공유 설정",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskShareRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n"
                                            + "  \"taskId\": 1,\n"
                                            + "  \"expirationDate\": \"2025-08-31\"\n"
                                            + "}"
                            )
                    )
            ) TaskShareRequest request
    );

    @Operation(summary = "공유 링크로 Task 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청(토큰 오류/만료/비활성화 등)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "토큰 불일치",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"Z03\",\n"
                                                    + "  \"message\": \"링크 토큰이 일치하지 않습니다.\"\n"
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "만료된 링크",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"Z05\",\n"
                                                    + "  \"message\": \"유효기간이 지난 링크입니다.\"\n"
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "비활성화된 링크",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"Z01\",\n"
                                                    + "  \"message\": \"공유가 비활성화된 링크입니다.)\"\n"
                                                    + "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "대상 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "할 일을 찾을 수 없음",
                                    value = "{\n"
                                            + "  \"status\": 404,\n"
                                            + "  \"code\": \"T01\",\n"
                                            + "  \"message\": \"해당 Task를 찾을 수 없습니다.\"\n"
                                            + "}"
                            )
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
    SuccessResponse<TaskResponse> getSharedTask(
            @Parameter(
                    description = "공유 토큰",
                    examples = @ExampleObject(value = "5Dorotxdu46F1P5MK113_A")
            ) String token
    );
}
