package com.todo.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.todo.task.application.dto.TaskResponse;
import com.todo.task.application.dto.TaskSearchRequest;
import com.todo.task.application.service.TaskQueryService;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.application.mapper.TaskMapper;
import com.todo.user.domain.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


    @Test
    @DisplayName("Task의 고유한 ID로 Task를 조회할 수 있다.")
    void 할일_검색_성공() {
        //given
        Task task = Task.builder()
                .id(1L)
                .title("TaskService 테스트 코드 작성")
                .content("Hello world")
                .status(TaskStatus.PROGRESS)
                .build();

        when(taskRepository.findTaskById(1L)).thenReturn(Optional.of(task));

        //when
        Task findTask = taskQueryService.findById(1L);

        //then
        assertThat(task.getId()).isEqualTo(findTask.getId());
        assertThat(task.getTitle()).isEqualTo(findTask.getTitle());
        assertThat(task.getStatus()).isEqualTo(TaskStatus.PROGRESS);
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
                    .startDate(start)
                    .endDate(start.plusDays(i))
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
                        t.getStartDate(),
                        t.getEndDate()
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