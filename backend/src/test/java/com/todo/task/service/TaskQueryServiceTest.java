package com.todo.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.repository.TaskRepository;
import com.todo.task.mapper.TaskMapper;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

}