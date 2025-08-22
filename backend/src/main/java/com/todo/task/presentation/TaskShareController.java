package com.todo.task.presentation;

import static org.springframework.http.HttpStatus.OK;

import com.todo.auth.domain.Auth;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import com.todo.task.application.dto.request.TaskShareRequest;
import com.todo.task.application.dto.request.TaskUpdateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.service.TaskCommandService;
import com.todo.task.application.service.TaskQueryService;
import com.todo.task.entity.vo.TaskShare;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share/tasks")
public class TaskShareController implements TaskShareApiDocs {

    private final TaskCommandService commandService;
    private final TaskQueryService queryService;

    @Auth
    @PostMapping
    public SuccessResponse<TaskShare> createTaskShareLink(@Login LoginUser user, @RequestBody TaskShareRequest req) {
        return SuccessResponse.success(OK, commandService.createShareLinkForTask(user.getUserId(), req));
    }

    @GetMapping("/{token}")
    public SuccessResponse<TaskResponse> getTaskByShareToken(@PathVariable("token") String token) {
        return SuccessResponse.success(OK, queryService.getTaskByShareToken(token));
    }

    @PatchMapping("/{token}")
    public SuccessResponse<TaskResponse> updateTaskByShareToken(@PathVariable("token") String token, @RequestBody
    TaskUpdateRequest request) {
        return SuccessResponse.success(OK, commandService.updateTaskByShareToken(token, request));
    }

}
