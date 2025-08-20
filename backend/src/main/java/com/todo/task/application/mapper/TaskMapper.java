package com.todo.task.application.mapper;

import com.todo.cateogry.domain.Category;
import com.todo.task.application.dto.request.TaskCreateRequest;
import com.todo.task.application.dto.response.TaskResponse;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import com.todo.task.entity.vo.TaskPeriod;
import com.todo.user.domain.User;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    default Task toEntity(User user, Category category, TaskCreateRequest req) {
        return Task.builder()
                .user(user)
                .category(category)
                .title(req.getTitle())
                .content(req.getContent())
                .period(new TaskPeriod(req.getStartDate(), req.getEndDate()))
                .status(TaskStatus.from(req.getStatus()))
                .build();
    }

    @Mapping(target = "categoryId", source = "category", qualifiedByName = "getCategoryId")
    @Mapping(target = "startDate", source = "period", qualifiedByName = "getStartDate")
    @Mapping(target = "endDate", source = "period", qualifiedByName = "getEndDate")
    TaskResponse toResponse(Task task);

    @Named("getCategoryId")
    default Long getCategoryId(Category category) {
        return category.getId();
    }

    @Named("getStartDate")
    default LocalDate getStartDate(TaskPeriod period) {
        return period.getStartDate();
    }

    @Named("getEndDate")
    default LocalDate getEndDate(TaskPeriod period) {
        return period.getEndDate();
    }

}
