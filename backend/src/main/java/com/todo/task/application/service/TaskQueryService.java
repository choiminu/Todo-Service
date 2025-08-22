package com.todo.task.application.service;

import static com.todo.common.exception.ErrorCode.TASK_NOT_FOUND;

import com.todo.common.utils.EncryptService;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskSearchRequest;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.entity.vo.TaskShare;
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
    private final EncryptService encryptService;

    public List<TaskResponse> searchTasksForUser(Long userId, TaskSearchRequest req) {
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

    public TaskResponse getTaskByShareToken(String token) {
        String decryptedToken = encryptService.decrypt(token);
        String[] tokenParts = decryptedToken.split(TaskShare.DELIMITER);

        Long userId = Long.valueOf(tokenParts[TaskShare.USER_ID_INDEX]);
        Long taskId = Long.valueOf(tokenParts[TaskShare.TASK_ID_INDEX]);

        Task findTask = findTaskByIdForUserOrThrow(taskId, userId);
        findTask.ensureValidShareToken(token);

        return taskMapper.toResponse(findTask);
    }

    public TaskResponse getTaskByIdForUser(Long taskId, Long userId) {
        return taskMapper.toResponse(findTaskByIdForUserOrThrow(taskId, userId));
    }

    public Task findTaskByIdForUserOrThrow(Long taskId, Long userId) {
        return taskRepository
                .findTaskByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskException(TASK_NOT_FOUND));
    }

}
