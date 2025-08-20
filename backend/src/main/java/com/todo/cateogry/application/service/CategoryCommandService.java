package com.todo.cateogry.application.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.application.dto.CategoryRequest;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.cateogry.application.mapper.CategoryMapper;
import com.todo.user.domain.User;
import com.todo.user.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryQueryService categoryQueryService;
    private final CategoryRepository categoryRepository;
    private final UserQueryService userQueryService;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(Long userId, CategoryRequest request) {
        User findUser = userQueryService.findUserById(userId);

        Category category = categoryMapper.categoryRequestToEntity(request);
        category.setUser(findUser);

        categoryRepository.save(category);
        return categoryMapper.EntityToCategoryResponse(category);
    }

    public CategoryResponse update(Long categoryId, Long userId, CategoryRequest request) {
        Category category = categoryQueryService.findById(categoryId);
        category.validateOwner(userId);
        category.categoryUpdate(request.getName());
        return categoryMapper.EntityToCategoryResponse(category);
    }

    public void delete(Long categoryId, Long userId) {
        Category category = categoryQueryService.findById(categoryId);
        category.validateOwner(userId);
        categoryRepository.delete(category);
    }
}
