package com.todo.task.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.service.CategoryQueryService;
import com.todo.task.dto.TaskCreateRequest;
import com.todo.task.dto.TaskResponse;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.user.domain.User;
import com.todo.user.service.UserDomainService;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskCommandServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserDomainService userDomainService;

    @Mock
    CategoryQueryService categoryQueryService;

    @InjectMocks
    TaskCommandService taskCommandService;

    User user;
    Category category;
    String email;
    String password;
    String confirmPassword;

    @BeforeEach
    void beforeEach() {
        this.email = "user@gmail.com";
        this.password = "user1234";
        this.confirmPassword = "user1234";

        user = User.builder()
                .id(1L)
                .email(email)
                .password(password)
                .build();

        category = Category.builder()
                .id(1L)
                .name("WORK")
                .user(user)
                .build();
    }

    @Test
    @DisplayName("사용자는 자신의 카테고리에 Task를 생성할 수 있다.")
    void 할일_생성_성공() {
        //given
        TaskCreateRequest req = new TaskCreateRequest(
                category.getId(),
                "테스트 코드 작성하기",
                "TaskService 테스트 코드 작성하자.",
                "NONE",
                LocalDate.now(),
                LocalDate.now()
        );

        Task task = Task.builder()
                .id(1L)
                .title(req.getTitle())
                .content(req.getContent())
                .status(TaskStatus.NONE)
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .user(user)
                .category(category)
                .build();

        when(categoryQueryService.findById(1L)).thenReturn(category);
        when(taskRepository.save(any())).thenReturn(task);

        //when
        TaskResponse response = taskCommandService.createTask(user.getId(), req);

        //then
        Assertions.assertThat(response.getTitle()).isEqualTo(req.getTitle());
        Assertions.assertThat(response.getContent()).isEqualTo(req.getContent());
        Assertions.assertThat(response.getCategoryId()).isEqualTo(req.getCategoryId());
        Assertions.assertThat(response.getStartDate()).isEqualTo(req.getStartDate());
        Assertions.assertThat(response.getEndDate()).isEqualTo(req.getEndDate());
        Assertions.assertThat(response.getStatus()).isEqualTo(TaskStatus.NONE);
    }

}