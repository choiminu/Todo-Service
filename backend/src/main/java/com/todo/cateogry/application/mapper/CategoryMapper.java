package com.todo.cateogry.application.mapper;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.application.dto.CategoryRequest;
import com.todo.cateogry.application.dto.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryRequestToEntity(CategoryRequest request);
    CategoryResponse EntityToCategoryResponse(Category category);
}
