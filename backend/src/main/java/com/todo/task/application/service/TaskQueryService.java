package com.todo.task.application.service;

import static com.todo.common.exception.ErrorCode.TASK_NOT_FOUND;

import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskSearchRequest;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.exception.TaskException;
import com.todo.task.application.mapper.TaskMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskQueryService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> searchUserTasks(Long userId, TaskSearchRequest req) {
        List<Task> tasks = taskRepository.search(
                userId,
                req.getCategoryId(),
                req.getStartDate(),
                req.getEndDate(),
                TaskStatus.from(req.getStatus())
        );
        return tasks.stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskByTaskIdAndUserId(Long taskId, Long userId) {
        return taskMapper.toResponse(findTaskByTaskIdAndUserId(taskId, userId));
    }

    public Task findTaskByTaskIdAndUserId(Long taskId, Long userId) {
        return taskRepository.findTaskByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskException(TASK_NOT_FOUND));
    }

}
