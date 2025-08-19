package com.todo.task.service;

import static com.todo.common.exception.ErrorCode.TASK_NOT_FOUND;

import com.todo.task.entity.Task;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.exception.TaskException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskQueryService {

    private final TaskRepository taskRepository;

    public Task findById(Long taskId) {
        return taskRepository.findTaskById(taskId)
                .orElseThrow(() -> new TaskException(TASK_NOT_FOUND));
    }

}
