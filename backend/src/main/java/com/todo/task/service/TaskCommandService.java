package com.todo.task.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.service.CategoryQueryService;
import com.todo.task.dto.TaskCreateRequest;
import com.todo.task.dto.TaskResponse;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    public TaskResponse createTask(Long userId, TaskCreateRequest request) {
        User findUser = userDomainService.findUserById(userId);
        Category findCategory = categoryQueryService.findByIdAndUserId(request.getCategoryId(), userId);

        Task task = taskMapper.taskCreateRequestToEntity(request);
        task.setUser(findUser);
        task.setCategory(findCategory);

        taskRepository.save(task);
        return taskMapper.entityToTaskResponse(task);
    }

}
