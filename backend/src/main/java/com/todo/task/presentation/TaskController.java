package com.todo.task.presentation;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.todo.auth.domain.Auth;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import com.todo.task.application.dto.request.TaskCreateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskSearchRequest;
import com.todo.task.application.dto.request.TaskUpdateRequest;
import com.todo.task.application.service.TaskCommandService;
import com.todo.task.application.service.TaskQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Auth
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskQueryService taskQueryService;
    private final TaskCommandService taskCommandService;

    @PostMapping
    public SuccessResponse<TaskResponse> createTask(
            @Login LoginUser loginUser,
            @RequestBody TaskCreateRequest request
    ) {
        return SuccessResponse.success(CREATED, taskCommandService.createTask(loginUser.getUserId(), request));
    }

    @GetMapping("/{id}")
    public SuccessResponse<TaskResponse> findTask(
            @PathVariable("id") Long taskId,
            @Login LoginUser loginUser
    ) {
        return SuccessResponse.success(OK, taskQueryService.getTaskByTaskIdAndUserId(taskId, loginUser.getUserId()));
    }

    @GetMapping
    public SuccessResponse<List<TaskResponse>> searchTask(
            @Login LoginUser loginUser,
            @ModelAttribute TaskSearchRequest request
    ) {
        return SuccessResponse.success(OK, taskQueryService.searchUserTasks(loginUser.getUserId(), request));
    }

    @PatchMapping("/{id}")
    public SuccessResponse<TaskResponse> updateTask(
            @PathVariable("id") Long taskId,
            @Login LoginUser loginUser,
            @RequestBody TaskUpdateRequest request
    ) {
       return SuccessResponse.success(OK, taskCommandService.updateTask(taskId, loginUser.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<Void> DeleteTask(
            @PathVariable("id") Long taskId,
            @Login LoginUser loginUser
    ) {
        taskCommandService.deleteTask(taskId, loginUser.getUserId());
        return SuccessResponse.success(NO_CONTENT);
    }

}
