package com.todo.task.service;

import static com.todo.common.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.exception.CategoryException;
import com.todo.cateogry.service.CategoryQueryService;
import com.todo.common.exception.ErrorCode;
import com.todo.task.dto.TaskCreateRequest;
import com.todo.task.dto.TaskResponse;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.mapper.TaskMapper;
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
    TaskMapper taskMapper;

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
    @DisplayName("사용자는 자신의 카테고리에 Task를 생성할 수 있다")
    void 할일_생성_성공() {
        // given
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

        TaskResponse resp = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getContent(),
                task.getStatus(),
                task.getStartDate(),
                task.getEndDate()
        );

        when(userDomainService.findUserById(user.getId())).thenReturn(user);
        when(categoryQueryService.findById(category.getId())).thenReturn(category);
        when(taskMapper.taskCreateRequestToEntity(req)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.entityToTaskResponse(task)).thenReturn(resp);

        // when
        TaskResponse result = taskCommandService.createTask(user.getId(), req);

        // then
        Assertions.assertThat(result.getTitle()).isEqualTo(req.getTitle());
        Assertions.assertThat(result.getContent()).isEqualTo(req.getContent());
        Assertions.assertThat(result.getCategoryId()).isEqualTo(req.getCategoryId());
        Assertions.assertThat(result.getStartDate()).isEqualTo(req.getStartDate());
        Assertions.assertThat(result.getEndDate()).isEqualTo(req.getEndDate());
        Assertions.assertThat(result.getStatus()).isEqualTo(TaskStatus.NONE);
    }

    @Test
    @DisplayName("카테고리가 없거나 다른 사용자의 카테고리 PK에 할일을 생성하면 예외가 발생한다.")
    void 할일_생성_실패_카테고리_없음() {
        // given
        TaskCreateRequest req = new TaskCreateRequest(
                999L,
                "테스트 코드 작성하기",
                "TaskService 테스트 코드 작성하자.",
                "NONE",
                LocalDate.now(),
                LocalDate.now()
        );

        when(categoryQueryService.findByIdAndUserId(any(), any())).thenThrow(new CategoryException(CATEGORY_NOT_FOUND));

        // when & then
        Assertions.assertThatThrownBy(() -> taskCommandService.createTask(user.getId(), req))
                .isInstanceOf(CategoryException.class)
                .hasMessage(CATEGORY_NOT_FOUND.getMessage());

    }

}