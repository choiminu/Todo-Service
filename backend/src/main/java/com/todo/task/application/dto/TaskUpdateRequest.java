package com.todo.task.application.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskUpdateRequest {
    private Long taskId;
    private String title;
    private String content;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
