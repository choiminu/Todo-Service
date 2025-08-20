package com.todo.task.entity.repository;

import com.todo.task.entity.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom{
    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Optional<Task> findTaskById(Long taskId);
}
