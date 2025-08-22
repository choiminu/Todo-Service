package com.todo.task.entity;

import com.todo.cateogry.domain.Category;
import com.todo.task.entity.vo.TaskPeriod;
import com.todo.task.entity.vo.TaskShare;
import com.todo.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Embedded
    private TaskPeriod period;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Embedded
    private TaskShare taskShare;

    public void taskUpdate(String title, String content, LocalDate startDate, LocalDate endDate, String status) {
        if (title != null) {
            this.title = title;
        }

        if (content != null) {
            this.content = content;
        }

        if (startDate != null || endDate != null) {
            this.period = period.updateTaskPeriod(startDate, endDate);
        }

        if (status != null) {
            this.status = TaskStatus.from(status);
        }
    }

    public TaskShare getShare(String link, LocalDate localDate) {
        return this.taskShare = new TaskShare(link, localDate);
    }

    public void isValidLink(String link) {
        this.taskShare.validateLink(link);
    }



}
