package com.todo.task.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskSearchRequest {
    private Long categoryId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
