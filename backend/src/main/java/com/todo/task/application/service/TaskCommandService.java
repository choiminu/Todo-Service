package com.todo.task.application.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.task.application.dto.request.TaskCreateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskUpdateRequest;
import com.todo.task.entity.Task;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.application.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskCommandService {

    private final TaskRepository taskRepository;
    private final TaskQueryService taskQueryService;
    private final CategoryQueryService categoryQueryService;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(Long userId, TaskCreateRequest req) {
        Category category = categoryQueryService.findCategoryByCategoryIdAndUserId(req.getCategoryId(), userId);

        Task task = taskMapper.toEntity(category.getUser(), category, req);
        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTask(Long taskId, Long userId, TaskUpdateRequest req) {
        Task findTask = taskQueryService.findTaskByTaskIdAndUserId(taskId, userId);
        findTask.taskUpdate(req.getTitle(), req.getContent(), req.getStartDate(), req.getEndDate(), req.getStatus());
        return taskMapper.toResponse(findTask);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task findTask = taskQueryService.findTaskByTaskIdAndUserId(taskId, userId);
        taskRepository.delete(findTask);
    }

}
