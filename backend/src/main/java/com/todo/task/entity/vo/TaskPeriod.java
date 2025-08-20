package com.todo.task.entity.vo;

import static com.todo.common.exception.ErrorCode.TASK_PERIOD_INVALID;

import com.todo.task.exception.TaskException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class TaskPeriod {

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public TaskPeriod(LocalDate startDate, LocalDate endDate) {
        validateTaskPeriod(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void validateTaskPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new TaskException(TASK_PERIOD_INVALID);
        }
    }

    public TaskPeriod updateTaskPeriod(LocalDate startDate, LocalDate endDate) {
        return new TaskPeriod(
                startDate != null ? startDate : this.startDate,
                endDate   != null ? endDate   : this.endDate
        );
    }

}
