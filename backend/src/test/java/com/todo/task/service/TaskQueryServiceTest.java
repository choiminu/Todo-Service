package com.todo.task.service;

import static com.todo.common.exception.ErrorCode.TASK_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.todo.common.exception.ErrorCode;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskSearchRequest;
import com.todo.task.application.service.TaskQueryService;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.application.mapper.TaskMapper;
import com.todo.task.entity.vo.TaskPeriod;
import com.todo.task.exception.TaskException;
import com.todo.user.domain.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskQueryServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @InjectMocks
    TaskQueryService taskQueryService;

    Task task;
    String title;
    String content;
    String status;
    LocalDate startDate;
    LocalDate endDate;

    @BeforeEach
    void beforeEach() {
        title = "알고리즘 공부";
        content = "DP 문제 풀기";
        status = "PROGRESS";
        startDate = LocalDate.now();
        endDate = LocalDate.now();

        task = Task.builder()
                .id(1L)
                .title(title)
                .content(content)
                .status(TaskStatus.from(status))
                .period(new TaskPeriod(startDate, endDate))
                .build();
    }

    @Test
    @DisplayName("사용자는 TaskId로 Task를 조회할 수 있다.")
    void findTaskByTaskIdAndUserId_success() {
        //given
        when(taskRepository.findTaskByTaskIdAndUserId(any(), any())).thenReturn(Optional.of(task));

        //when
        Task findTask = taskQueryService.findTaskByTaskIdAndUserId(1L, 1L);

        //then
        assertThat(task.getId()).isEqualTo(findTask.getId());
        assertThat(task.getTitle()).isEqualTo(findTask.getTitle());
        assertThat(task.getStatus()).isEqualTo(TaskStatus.PROGRESS);
    }

    @Test
    @DisplayName("Task를 조회할때 Task가 존재하지 않는다면 예외가 발생한다.")
    void findTaskByTaskIdAndUserId_fail_when_taskNotFound() {
        //given
        when(taskRepository.findTaskByTaskIdAndUserId(any(), any())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() ->  taskQueryService.findTaskByTaskIdAndUserId(1L, 1L))
                .isInstanceOf(TaskException.class)
                .hasMessage(TASK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("검색 조건대로 Task를 조회하고 Mapper로 변환해 리스트를 반환한다")
    void 할일_검색_필터링_성공() {
        // given
        User user = User.builder().id(1L).build();

        List<Task> tasks = new ArrayList<>();
        LocalDate start = LocalDate.of(2025, 8, 20);
        for (long i = 1L; i <= 10L; i++) {
            tasks.add(Task.builder()
                    .id(i)
                    .user(user)
                    .status(TaskStatus.NONE)
                    .period(new TaskPeriod(start, start.plusDays(1)))
                    .build());
        }

        TaskSearchRequest req =
                new TaskSearchRequest(1L, "NONE", LocalDate.now(), LocalDate.now());

        try (MockedStatic<TaskStatus> mocked = mockStatic(TaskStatus.class)) {
            mocked.when(() -> TaskStatus.from("NONE"))
                    .thenReturn(TaskStatus.NONE);

            when(taskRepository.search(
                    eq(user.getId()),
                    eq(req.getCategoryId()),
                    eq(req.getStartDate()),
                    eq(req.getEndDate()),
                    eq(TaskStatus.NONE)
            )).thenReturn(tasks);

            when(taskMapper.entityToTaskResponse(any(Task.class))).thenAnswer(inv -> {
                Task t = inv.getArgument(0);
                return new TaskResponse(
                        t.getId(),
                        null,
                        null,
                        t.getStatus(),
                        t.getPeriod().getStartDate(),
                        t.getPeriod().getEndDate()
                );
            });

            // when
            List<TaskResponse> all = taskQueryService.findAll(user.getId(), req);

            // then
            assertThat(all).hasSize(10);

            verify(taskRepository).search(
                    user.getId(),
                    req.getCategoryId(),
                    req.getStartDate(),
                    req.getEndDate(),
                    TaskStatus.NONE
            );

            verify(taskMapper, times(10)).entityToTaskResponse(any(Task.class));
            verifyNoMoreInteractions(taskRepository, taskMapper);
        }
    }

}