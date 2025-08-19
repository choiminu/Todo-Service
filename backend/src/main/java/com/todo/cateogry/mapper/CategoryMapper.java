package com.todo.cateogry.mapper;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.dto.CategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryRequestToEntity(CategoryRequest request);
}
