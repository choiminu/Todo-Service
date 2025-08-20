package com.todo.task.dto;

import com.todo.task.entity.TaskStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    private Long categoryId;
    private String title;
    private String content;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
