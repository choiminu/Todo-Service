package com.todo.task.presentation;

import com.todo.common.response.ErrorResponse;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import com.todo.task.application.dto.request.TaskCreateRequest;
import com.todo.task.application.dto.request.TaskSearchRequest;
import com.todo.task.application.dto.request.TaskUpdateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Task", description = "할 일(Task) 관련 API")
public interface TaskApiDocs {

    @Operation(summary = "할 일 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(
                                    name = "요청 형식 오류",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"T01\",\n"
                                            + "  \"message\": \"요청 값이 올바르지 않습니다.\"\n"
                                            + "}"
                            ), @ExampleObject(
                                    name = "카테고리 없음",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"C01\",\n"
                                            + "  \"message\": \"카테고리를 찾을 수 없습니다.\"\n"
                                            + "}"
                            ),
                                    @ExampleObject(
                                            name = "유효하지 않을 날짜",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"T03\",\n"
                                                    + "  \"message\": \"유효하지 않은 기간입니다.\"\n"
                                                    + "}"
                                    )}
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "접근권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "접근권한부족",
                                            value = "{\n"
                                                    + "  \"status\": 401,\n"
                                                    + "  \"code\": \"U01\",\n"
                                                    + "  \"message\": \"로그인이 필요한 서비스입니다.\"\n"
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
    SuccessResponse<TaskResponse> createTask(
            @Login LoginUser loginUser,
            @RequestBody(
                    description = "할 일 생성 정보",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskCreateRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n"
                                            + "  \"categoryId\": 1,\n"
                                            + "  \"title\": \"운동하기\",\n"
                                            + "  \"content\": \"저녁 7시 러닝\",\n"
                                            + "  \"startDate\": \"2025-08-21\",\n"
                                            + "  \"endDate\": \"2025-08-21\"\n"
                                            + "}"
                            )
                    )
            ) TaskCreateRequest request
    );

    @Operation(summary = "할 일 단건 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "존재하지 않은 Task",
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
                            examples = {
                                    @ExampleObject(
                                            name = "접근권한부족",
                                            value = "{\n"
                                                    + "  \"status\": 401,\n"
                                                    + "  \"code\": \"U01\",\n"
                                                    + "  \"message\": \"로그인이 필요한 서비스입니다.\"\n"
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
                                            + "  \"code\": \"T04\",\n"
                                            + "  \"message\": \"할 일을 찾을 수 없습니다.\"\n"
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
    SuccessResponse<TaskResponse> findTask(
            @Parameter(description = "조회할 Task ID", content = @Content(
                    schema = @Schema(implementation = Long.class),examples = @ExampleObject(value = "1")
            )) Long taskId,
            @Login LoginUser loginUser
    );

    @Operation(summary = "할 일 검색 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(
                    responseCode = "401",
                    description = "접근권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "접근권한부족",
                                            value = "{\n"
                                                    + "  \"status\": 401,\n"
                                                    + "  \"code\": \"U01\",\n"
                                                    + "  \"message\": \"로그인이 필요한 서비스입니다.\"\n"
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
    SuccessResponse<List<TaskResponse>> searchTask(
            @Login LoginUser loginUser,
            @RequestBody(
                    description = "할 일 검색 조건",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskSearchRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n"
                                            + "  \"categoryId\": 1,\n"
                                            + "  \"status\": \"NONE\",\n"
                                            + "  \"startDate\": \"2025-08-21\",\n"
                                            + "  \"endDate\": \"2025-08-21\"\n"
                                            + "}"
                            )
                    )
            ) TaskSearchRequest request
    );

    @Operation(summary = "할 일 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "요청 형식 오류",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"T01\",\n"
                                            + "  \"message\": \"요청 값이 올바르지 않습니다.\"\n"
                                            + "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "접근권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "접근권한부족",
                                            value = "{\n"
                                                    + "  \"status\": 401,\n"
                                                    + "  \"code\": \"U01\",\n"
                                                    + "  \"message\": \"로그인이 필요한 서비스입니다.\"\n"
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
                                            + "  \"code\": \"T04\",\n"
                                            + "  \"message\": \"할 일을 찾을 수 없습니다.\"\n"
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
    SuccessResponse<TaskResponse> updateTask(
            @Parameter(description = "수정할 Task ID", content = @Content(
                    schema = @Schema(implementation = Long.class),examples = @ExampleObject(value = "1")
            )) Long taskId,
            @Login LoginUser loginUser,
            @RequestBody(
                    description = "할 일 수정 정보",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskUpdateRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n"
                                            + "  \"title\": \"운동하기(수정)\",\n"
                                            + "  \"content\": \"러닝 → 헬스\",\n"
                                            + "  \"status\": \"PROGRESS\"\n"
                                            + "}"
                            )
                    )
            ) TaskUpdateRequest request
    );

    @Operation(summary = "할 일 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(
                    responseCode = "401",
                    description = "접근권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "접근권한부족",
                                            value = "{\n"
                                                    + "  \"status\": 401,\n"
                                                    + "  \"code\": \"U01\",\n"
                                                    + "  \"message\": \"로그인이 필요한 서비스입니다.\"\n"
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
                                            + "  \"code\": \"T04\",\n"
                                            + "  \"message\": \"할 일을 찾을 수 없습니다.\"\n"
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
    SuccessResponse<Void> deleteTask(
            @Parameter(description = "삭제할 Task ID", content = @Content(
                    schema = @Schema(implementation = Long.class),examples = @ExampleObject(value = "1")
            )) Long taskId,
            @Login LoginUser loginUser
    );
}
