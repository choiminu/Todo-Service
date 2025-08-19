package com.todo.task.service;

import static com.todo.common.exception.ErrorCode.TASK_NOT_FOUND;

import com.todo.auth.domain.Auth;
import com.todo.task.dto.TaskResponse;
import com.todo.task.dto.TaskSearchRequest;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.exception.TaskException;
import com.todo.task.mapper.TaskMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskQueryService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> findAll(Long userId, TaskSearchRequest request) {

        List<Task> tasks = taskRepository.search(
                userId,
                request.getCategoryId(),
                request.getStartDate(),
                request.getEndDate(),
                TaskStatus.from(request.getStatus())
        );

        List<TaskResponse> result = new ArrayList<>();

        for (Task task : tasks) {
            result.add(taskMapper.entityToTaskResponse(task));
        }

        return result;
    }

    public Task findById(Long taskId) {
        return taskRepository.findTaskById(taskId)
                .orElseThrow(() -> new TaskException(TASK_NOT_FOUND));
    }

}
