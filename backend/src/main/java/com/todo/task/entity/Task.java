package com.todo.task.entity;

import static com.todo.common.exception.ErrorCode.TASK_ACCESS_FORBIDDEN;

import com.todo.cateogry.domain.Category;
import com.todo.task.exception.TaskException;
import com.todo.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2550)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void validateOwner(Long userId) {
        if (!(this.user.getId().equals(userId))) {
            throw new TaskException(TASK_ACCESS_FORBIDDEN);
        }
    }

    public void taskUpdate(String title, String content, LocalDate startDate, LocalDate endDate, String status) {
        if (title != null) {
            this.title = title;
        }

        if (content != null) {
            this.content = content;
        }

        if (startDate != null) {
            this.startDate = startDate;
        }

        if (endDate != null) {
            this.endDate = endDate;
        }

        if (status != null) {
            this.status = TaskStatus.from(status);
        }
    }
}
