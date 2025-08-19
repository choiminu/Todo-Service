package com.todo.task.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.service.CategoryQueryService;
import com.todo.task.dto.TaskCreateRequest;
import com.todo.task.dto.TaskResponse;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.user.domain.User;
import com.todo.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskCommandService {

    private final TaskRepository taskRepository;
    private final UserDomainService userDomainService;
    private final CategoryQueryService categoryQueryService;

    public TaskResponse createTask(Long userId, TaskCreateRequest request) {
        User findUser = userDomainService.findUserById(userId);
        Category category = categoryQueryService.findById(request.getCategoryId());

        Task task = Task.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(TaskStatus.valueOf(request.getStatus()))
                .user(findUser)
                .category(category)
                .build();

        taskRepository.save(task);
        return new TaskResponse(category.getId(), task.getTitle(), task.getContent(), task.getStatus(), task.getStartDate(), task.getEndDate());
    }

}
