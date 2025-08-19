package com.todo.task.mapper;

import com.todo.cateogry.domain.Category;
import com.todo.task.dto.TaskCreateRequest;
import com.todo.task.dto.TaskResponse;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToTaskStatus")
    Task taskCreateRequestToEntity(TaskCreateRequest request);

    @Mapping(target = "categoryId", source = "category", qualifiedByName = "getCategoryId")
    TaskResponse entityToTaskResponse(Task task);

    @Named("getCategoryId")
    default Long getCategoryId(Category category) {
        return category.getId();
    }

    @Named("stringToTaskStatus")
    default TaskStatus stringToTaskStatus(String status) {
        if (status == null) return TaskStatus.NONE;
        return TaskStatus.from(status);
    }
}
