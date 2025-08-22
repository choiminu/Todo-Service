package com.todo.task.entity;

import static com.todo.common.exception.ErrorCode.DISABLED_LINK;
import static com.todo.common.exception.ErrorCode.INVALID_EXPIRATION_DATE;
import static com.todo.common.exception.ErrorCode.TOKEN_MISMATCH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.todo.task.exception.TaskException;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TaskShareTest {

    @ParameterizedTest
    @ValueSource(strings = {"abc123", "test1234"})
    @DisplayName("유효한 링크는 정상적으로 URL을 반환한다.")
    void createLink_Success(String link) {
        //given
        TaskShare ts = new TaskShare(link, LocalDate.now());

        //when
        String taskLink = ts.getTaskLink();

        //then
        Assertions.assertThat(taskLink).isEqualTo("http://localhost:8080/api/public/tasks/" + link);
    }

    @Test
    @DisplayName("비활성화된 링크에 접근하면 예외가 발생한다.")
    public void should_throw_Exception_when_accessing_disabledLink() {
        //given
        TaskShare taskShare = new TaskShare();

        //when & then
        assertThatThrownBy(() -> taskShare.validateLink("abc123"))
                .isInstanceOf(TaskException.class)
                .hasMessage(DISABLED_LINK.getMessage());
    }

    @Test
    @DisplayName("만료일이 잘못된 경우 예외가 발생한다.")
    public void should_throw_exception_when_expiration_date_is_invalid() {
        //given &when & then
        assertThatThrownBy(() -> new TaskShare("abc123", LocalDate.now().minusDays(1)))
                .isInstanceOf(TaskException.class)
                .hasMessage(INVALID_EXPIRATION_DATE.getMessage());
    }

    @Test
    @DisplayName("링크 토큰이 일치하지 않는 경우 예외가 발생한다.")
    public void should_throw_exception_when_shared_link_not_matching() {
        //given
        TaskShare taskShare = new TaskShare("abc123", LocalDate.now());

        //when & then
        Assertions.assertThatThrownBy(() -> taskShare.validateLink("321cba"))
                .isInstanceOf(TaskException.class)
                .hasMessage(TOKEN_MISMATCH.getMessage());
    }
}