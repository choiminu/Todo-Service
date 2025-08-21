package com.todo.cateogry.presentation;

import com.todo.cateogry.application.dto.CategoryRequest;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.common.response.ErrorResponse;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Category", description = "카테고리 관련 API")
public interface CategoryApiDocs {

    @Operation(summary = "카테고리 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "요청 형식 오류",
                                            value = "{\n"
                                                    + "  \"status\": 400,\n"
                                                    + "  \"code\": \"C01\",\n"
                                                    + "  \"message\": \"요청 값이 올바르지 않습니다.\"\n"
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
    SuccessResponse<CategoryResponse> createCategory(
            @Login LoginUser user,
            @RequestBody(
                    description = "카테고리 생성 정보",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n"
                                            + "  \"name\": \"일상\"\n"
                                            + "}"
                            )
                    )
            ) CategoryRequest request
    );

    @Operation(summary = "카테고리 목록 조회 API")
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
    SuccessResponse<List<CategoryResponse>> findCategories(@Login LoginUser user);

    @Operation(summary = "카테고리 수정 API")
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
                                            + "  \"code\": \"C01\",\n"
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
                    responseCode = "400",
                    description = "대상 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "카테고리 없음",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"C01\",\n"
                                            + "  \"message\": \"카테고리를 찾을 수 없습니다.\"\n"
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
    SuccessResponse<CategoryResponse> updateCategory(
            @Login LoginUser user,
            @Parameter(description = "수정할 Category ID", content = @Content(
                    schema = @Schema(implementation = Long.class), examples = @ExampleObject(value = "1")
            )) Long categoryId,
            @RequestBody(
                    description = "카테고리 수정 정보",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n"
                                            + "  \"name\": \"업무\"\n"
                                            + "}"
                            )
                    )
            ) CategoryRequest request
    );

    @Operation(summary = "카테고리 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "대상 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "카테고리 없음",
                                    value = "{\n"
                                            + "  \"status\": 400,\n"
                                            + "  \"code\": \"C01\",\n"
                                            + "  \"message\": \"카테고리를 찾을 수 없습니다.\"\n"
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
    SuccessResponse<Void> deleteCategory(@Login LoginUser user,
                                         @Parameter(description = "삭제할 Category ID", content = @Content(
                                                 schema = @Schema(implementation = Long.class), examples = @ExampleObject(value = "1")
                                         )) Long categoryId);
}
