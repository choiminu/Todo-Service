package com.todo.task.service;

import static com.todo.common.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static com.todo.common.exception.ErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.exception.CategoryException;
import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.common.exception.ErrorCode;
import com.todo.task.application.dto.request.TaskCreateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskUpdateRequest;
import com.todo.task.application.service.TaskCommandService;
import com.todo.task.application.service.TaskQueryService;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.entity.vo.TaskPeriod;
import com.todo.task.exception.TaskException;
import com.todo.task.application.mapper.TaskMapper;
import com.todo.user.domain.User;
import com.todo.user.application.service.UserQueryService;
import com.todo.user.exception.UserException;
import java.time.LocalDate;
import net.bytebuddy.asm.Advice.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskCommandServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserQueryService userQueryService;

    @Mock
    TaskMapper taskMapper;

    @Mock
    CategoryQueryService categoryQueryService;

    @Mock
    TaskQueryService taskQueryService;

    @InjectMocks
    TaskCommandService taskCommandService;

    User user;
    Category category;
    String email;
    String password;
    String confirmPassword;

    Task task;

    String title;
    String content;
    String status;
    LocalDate startDate;
    LocalDate endDate;

    @BeforeEach
    void beforeEach() {
        this.email = "user@gmail.com";
        this.password = "user1234";
        this.confirmPassword = "user1234";

        title = "알고리즘 공부";
        content = "DP 문제 풀기";
        status = "PROGRESS";
        startDate = LocalDate.now();
        endDate = LocalDate.now();

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

        task = Task.builder()
                .id(1L)
                .title(title)
                .content(content)
                .status(TaskStatus.from(status))
                .period(new TaskPeriod(startDate, endDate))
                .build();

    }

    @Test
    @DisplayName("사용자가 생성한 카테고리 내부에 Task를 생성할 수 있다.")
    void createTask_Success() {
        //given
        TaskCreateRequest req = new TaskCreateRequest(1L, title, content, status, startDate, endDate);
        TaskResponse res = new TaskResponse(1L, title, content, TaskStatus.PROGRESS, startDate, endDate);

        when(taskMapper.toEntity(any(), any(), any())).thenReturn(task);
        when(taskMapper.entityToTaskResponse(task)).thenReturn(res);

        //when
        TaskResponse response = taskCommandService.createTask(1L, req);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getCategoryId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo(title);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getStatus()).isEqualTo(TaskStatus.PROGRESS);
    }

    @Test
    @DisplayName("Task 생성 시 카테고리가 없다면 예외가 발생한다.")
    void createTask_fail_when_categoryNotFound() {
        //given
        TaskCreateRequest req = new TaskCreateRequest(1L, title, content, status, startDate, endDate);

        when(categoryQueryService.findCategoryByCategoryIdAndUserId(1L, 1L)).thenThrow(
                new CategoryException(CATEGORY_NOT_FOUND));

        //when & then
        Assertions.assertThatThrownBy(() -> taskCommandService.createTask(1L, req))
                .isInstanceOf(CategoryException.class)
                .hasMessage(CATEGORY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Task 생성 시 사용자가 존재하지 않는다면 예외가 발생한다.")
    void createTask_fail_when_userNotFound() {
        //given
        TaskCreateRequest req = new TaskCreateRequest(1L, title, content, status, startDate, endDate);

        when(userQueryService.findUserById(any())).thenThrow(
                new UserException(USER_NOT_FOUND));

        //when & then
        Assertions.assertThatThrownBy(() -> taskCommandService.createTask(1L, req))
                .isInstanceOf(UserException.class)
                .hasMessage(USER_NOT_FOUND.getMessage());
    }


    @Test
    @DisplayName("사용자는 자신의 Task의 정보를 수정할 수 있다.")
    void 할일_수정_성공() {
        //given
        Task task = Task.builder()
                .id(1L)
                .title("Todo 작성")
                .content("HELLO WORLD")
                .status(TaskStatus.NONE)
                .period(new TaskPeriod(LocalDate.now(), LocalDate.now()))
                .user(user)
                .category(category)
                .build();

        TaskUpdateRequest req = new TaskUpdateRequest(
                1L,
                "Todo 수정",
                "hello world",
                "PROGRESS",
                LocalDate.now(),
                LocalDate.now()
        );

        TaskResponse resp = new TaskResponse(
                task.getId(),
                req.getTitle(),
                req.getContent(),
                TaskStatus.PROGRESS,
                req.getStartDate(),
                req.getEndDate()
        );

        when(taskQueryService.findById(1L)).thenReturn(task);
        when(taskMapper.entityToTaskResponse(task)).thenReturn(resp);

        //when
        TaskResponse response = taskCommandService.updateTask(user.getId(), req);

        //then
        assertThat(response.getTitle()).isEqualTo(req.getTitle());
    }

    @Test
    @DisplayName("자신의 소유가 아닌 Task를 수정할 경우 예외가 발생한다.")
    void 할일_수정_실패() {
        //given
        User anotherUser = User.builder()
                .id(2L)
                .build();

        Task task = Task.builder()
                .id(1L)
                .title("Todo 작성")
                .content("HELLO WORLD")
                .status(TaskStatus.NONE)
                .period(new TaskPeriod(LocalDate.now(), LocalDate.now()))
                .user(anotherUser)
                .category(category)
                .build();

        TaskUpdateRequest req = new TaskUpdateRequest(
                1L,
                "Todo 수정",
                "hello world",
                "PROGRESS",
                LocalDate.now(),
                LocalDate.now()
        );

        when(taskQueryService.findById(any())).thenReturn(task);

        //when & then
        assertThatThrownBy(() -> taskCommandService.updateTask(user.getId(), req))
                .isInstanceOf(TaskException.class)
                .hasMessage(ErrorCode.TASK_ACCESS_FORBIDDEN.getMessage());
    }

    @Test
    @DisplayName("사용자는 자신의 Task를 삭제할 수 있다.")
    void 할일_삭제_성공() {
        //given
        Task task = Task.builder()
                .id(1L)
                .title("Todo 작성")
                .content("HELLO WORLD")
                .status(TaskStatus.NONE)
                .period(new TaskPeriod(LocalDate.now(), LocalDate.now()))
                .user(user)
                .category(category)
                .build();

        when(taskQueryService.findById(1L)).thenReturn(task);

        //when
        taskCommandService.deleteTask(user.getId(), 1L);

        //then
        verify(taskRepository).delete(task);
    }

    @Test
    @DisplayName("다른 사용자의 Task를 삭제할 경우 예외가 발생한다.")
    void 할일_삭제_실패() {
        //given
        User anotherUser = User.builder()
                .id(2L)
                .build();

        Task task = Task.builder()
                .id(1L)
                .title("Todo 작성")
                .content("HELLO WORLD")
                .status(TaskStatus.NONE)
                .period(new TaskPeriod(LocalDate.now(), LocalDate.now()))
                .user(anotherUser)
                .category(category)
                .build();

        when(taskQueryService.findById(1L)).thenReturn(task);

        //when
        assertThatThrownBy(() -> taskCommandService.deleteTask(user.getId(), 1L))
                .isInstanceOf(TaskException.class);
    }

}