package com.todo.task.service;

import static com.todo.common.exception.ErrorCode.TASK_NOT_FOUND;
import static com.todo.task.service.TaskCommandServiceTest.STUB_CATEGORY_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.todo.common.utils.EncryptService;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.application.dto.request.TaskSearchRequest;
import com.todo.task.application.service.TaskQueryService;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.application.mapper.TaskMapper;
import com.todo.task.entity.vo.TaskPeriod;
import com.todo.task.entity.vo.TaskShare;
import com.todo.task.exception.TaskException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskQueryServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @Mock
    EncryptService encryptService;

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
                .taskShare(new TaskShare("5Dorotxdu46F1P5MK113_A", LocalDate.now()))
                .build();
    }

    @Test
    @DisplayName("사용자는 TaskId로 Task를 조회할 수 있다.")
    void findTaskById_ForUserOrThrow_success() {
        //given
        when(taskRepository.findTaskByTaskIdAndUserId(any(), any())).thenReturn(Optional.of(task));

        //when
        Task findTask = taskQueryService.findTaskByIdForUserOrThrow(1L, 1L);

        //then
        assertThat(task.getId()).isEqualTo(findTask.getId());
        assertThat(task.getTitle()).isEqualTo(findTask.getTitle());
        assertThat(task.getStatus()).isEqualTo(TaskStatus.PROGRESS);
    }

    @Test
    @DisplayName("Task를 조회할때 Task가 존재하지 않는다면 예외가 발생한다.")
    void findTaskByTaskIdAndUserId_fail_when_taskNotFoundForUserOrThrow() {
        //given
        when(taskRepository.findTaskByTaskIdAndUserId(any(), any())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> taskQueryService.findTaskByIdForUserOrThrow(1L, 1L))
                .isInstanceOf(TaskException.class)
                .hasMessage(TASK_NOT_FOUND.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1, Todo Service 구현하기, Todo Service를 만들어보자., 2025-08-20, 2025-08-24, NONE",
            "3, Todo Service 구현하기, Todo Service를 만들어보자., 2025-08-20, 2025-08-24, PROGRESS",
            "5, Todo Service 구현하기, Todo Service를 만들어보자., 2025-08-20, 2025-08-24, DONE",
    })
    @DisplayName("사용자는 카테고리 Id, 상태, 시작일, 종료일을 조합하여 Task를 검색할 수 있다.")
    void searchTasks_ForUser_success(int size, String title, String content, LocalDate startDate, LocalDate endDate,
                                     String status) {
        //given

        TaskSearchRequest req = new TaskSearchRequest(null, status, startDate, endDate);

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            taskList.add(Task.builder()
                    .title(title)
                    .content(content)
                    .period(new TaskPeriod(startDate, endDate))
                    .status(TaskStatus.from(status))
                    .build());
        }

        when(taskRepository.search(any(), any(), any(), any(), any())).thenReturn(taskList);

        //when
        List<TaskResponse> responses = taskQueryService.searchTasksForUser(1L, req);

        //then
        assertThat(responses.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("유효한 토큰으로 Task 공유 조회 시 TaskResponse를 반환한다.")
    void enableSharingTask_success_when_valid_token() {
        // given
        String token = "5Dorotxdu46F1P5MK113_A";
        TaskResponse res = new TaskResponse(task.getId(), STUB_CATEGORY_ID, title, content, TaskStatus.PROGRESS,
                startDate, endDate);

        when(taskRepository.findTaskByTaskIdAndUserId(any(), any())).thenReturn(Optional.of(task));
        when(encryptService.decrypt(token)).thenReturn("1:1");
        when(taskMapper.toResponse(task)).thenReturn(res);

        // when
        TaskResponse shareTask = taskQueryService.getTaskByShareToken(token);

        // then
        assertThat(shareTask).isNotNull();
        assertThat(shareTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(shareTask.getContent()).isEqualTo(task.getContent());
    }


}