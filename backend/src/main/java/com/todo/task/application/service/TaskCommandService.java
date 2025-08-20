package com.todo.task.application.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.task.application.dto.request.TaskCreateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskUpdateRequest;
import com.todo.task.entity.Task;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.application.mapper.TaskMapper;
import com.todo.user.domain.User;
import com.todo.user.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskCommandService {

    private final TaskRepository taskRepository;
    private final TaskQueryService taskQueryService;
    private final UserQueryService userQueryService;
    private final CategoryQueryService categoryQueryService;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(Long userId, TaskCreateRequest req) {

        // 세션에 저장된 사용자의 id를 매개변수로 받아 사용자 엔티티를 조회한다.
        User user = userQueryService.findUserById(userId);

        // 요청 DTO의 categoryId와 userId로 해당 사용자의 카테고리를 단일 조회한다 (소유자 검증 겸용).
        Category category = categoryQueryService.findCategoryByCategoryIdAndUserId(req.getCategoryId(), userId);

        // 위 과정에서 조회된 사용자,카테고리와 요청 DTO를 매핑해 Task 엔티티를 생성한다.
        Task task = taskMapper.toEntity(user, category, req);

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTask(Long taskId, Long userId, TaskUpdateRequest req) {

        // 세션에 저장된 사용자의 id와 Task id로 DB에 저장된 Task를 조회한다.
        Task findTask = taskQueryService.findTaskByTaskIdAndUserId(taskId, userId);

        // 요청 DTO를 기반으로 조회한 Task의 상태를 업데이트한다.
        findTask.taskUpdate(req.getTitle(), req.getContent(), req.getStartDate(), req.getEndDate(), req.getStatus());

        return taskMapper.toResponse(findTask);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task findTask = taskQueryService.findTaskByTaskIdAndUserId(taskId, userId);
        taskRepository.delete(findTask);
    }

}
