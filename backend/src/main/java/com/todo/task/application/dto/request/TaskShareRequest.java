package com.todo.task.application.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskShareRequest {
    private Long taskId;
    private LocalDate expirationDate;
}
