package com.todo.task.entity.repository;

import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepositoryCustom {
    List<Task> search(Long memberId, Long categoryId, LocalDate from, LocalDate to, TaskStatus status);
}
