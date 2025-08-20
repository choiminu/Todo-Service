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

        // 사용자 ID와 요청의 검색 조건(categoryId, 기간[startDate~endDate], 상태)을 기반으로 작업 목록을 조회한다.
        List<Task> tasks = taskRepository.search(
                userId,
                req.getCategoryId(),
                req.getStartDate(),
                req.getEndDate(),
                TaskStatus.from(req.getStatus())
        );

        // TaskMapper 클래스를 통해 조회된 Task List를 응답 DTO로 변환하여 반환한다.
        return tasks.stream()
                .map(taskMapper::entityToTaskResponse)
                .toList();
    }

    public Task findTaskByTaskIdAndUserId(Long taskId, Long userId) {
        return taskRepository.findTaskByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskException(TASK_NOT_FOUND));
    }

}
