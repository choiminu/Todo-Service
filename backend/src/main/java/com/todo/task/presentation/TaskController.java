package com.todo.task.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import com.todo.task.dto.TaskCreateRequest;
import com.todo.task.dto.TaskResponse;
import com.todo.task.dto.TaskSearchRequest;
import com.todo.task.dto.TaskUpdateRequest;
import com.todo.task.service.TaskCommandService;
import com.todo.task.service.TaskQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @GetMapping
    public SuccessResponse<List<TaskResponse>> findTask(
            @Login LoginUser loginUser,
            @ModelAttribute TaskSearchRequest request
    ) {
        return SuccessResponse.success(HttpStatus.OK, taskQueryService.findAll(loginUser.getUserId(), request));
    }

    @PutMapping
    public SuccessResponse<TaskResponse> updateTask(@Login LoginUser loginUser, @RequestBody TaskUpdateRequest request) {
       return SuccessResponse.success(HttpStatus.OK, taskCommandService.updateTask(loginUser.getUserId(), request));
    }

    @DeleteMapping
    public SuccessResponse<Void> DeleteTask(@Login LoginUser loginUser, @RequestParam Long taskId) {
        taskCommandService.deleteTask(loginUser.getUserId(), taskId);
        return SuccessResponse.success(HttpStatus.OK);
    }

}
