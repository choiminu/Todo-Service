package com.todo.task.application.dto.request;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskSearchRequest {
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "상태", example = "PROGRESS")
    private String status;

    @Schema(description = "시작일", example = "2025-08-20")
    private LocalDate startDate;

    @Schema(description = "종료일", example = "2025-08-25")
    private LocalDate endDate;
}
