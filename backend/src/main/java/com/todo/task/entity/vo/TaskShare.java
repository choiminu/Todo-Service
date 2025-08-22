package com.todo.task.entity.vo;


import static com.todo.common.exception.ErrorCode.DISABLED_LINK;
import static com.todo.common.exception.ErrorCode.EXPIRED_LINK;
import static com.todo.common.exception.ErrorCode.INVALID_EXPIRATION_DATE;
import static com.todo.common.exception.ErrorCode.INVALID_LINK;
import static com.todo.common.exception.ErrorCode.TOKEN_MISMATCH;

import com.todo.task.exception.TaskException;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class TaskShare {

    public static final String TASK_SHARED_BASE_URL = "http://localhost:8080/api/public/tasks/";
    public static final String DELIMITER = ":";
    public static final int USER_ID_INDEX = 0;
    public static final int TASK_ID_INDEX = 1;

    private boolean shared;
    private String sharedLink;
    private LocalDate expirationDate;

    public TaskShare(String link, LocalDate localDate) {
        validateInitialExpirationDate(localDate);
        this.shared = true;
        this.sharedLink = link;
        this.expirationDate = localDate;
    }

    public void validateLink(String link) {
        if (!shared) {
            throw new TaskException(DISABLED_LINK);
        }

        if (sharedLink == null || expirationDate == null) {
            throw new TaskException(INVALID_LINK);
        }

        if (!sharedLink.equals(link)) {
            throw new TaskException(TOKEN_MISMATCH);
        }

        if (expirationDate.isBefore(LocalDate.now())) {
            throw new TaskException(EXPIRED_LINK);
        }
    }

    private void validateInitialExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null ||expirationDate.isBefore(LocalDate.now())) {
            throw new TaskException(INVALID_EXPIRATION_DATE);
        }
    }

    public String getTaskLink() {
        validateLink(this.sharedLink);
        return TASK_SHARED_BASE_URL + sharedLink;
    }

}
