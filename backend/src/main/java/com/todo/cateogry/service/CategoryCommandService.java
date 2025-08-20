package com.todo.cateogry.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.dto.CategoryRequest;
import com.todo.cateogry.dto.CategoryResponse;
import com.todo.cateogry.mapper.CategoryMapper;
import com.todo.user.domain.User;
import com.todo.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryQueryService categoryQueryService;
    private final CategoryRepository categoryRepository;
    private final UserCommandService userCommandService;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(Long userId, CategoryRequest request) {
        User findUser = userCommandService.findUserById(userId);

        Category category = categoryMapper.categoryRequestToEntity(request);
        category.setUser(findUser);

        categoryRepository.save(category);
        return categoryMapper.EntityToCategoryResponse(category);
    }

    public CategoryResponse update(Long categoryId, Long userId, CategoryRequest request) {
        Category category = categoryQueryService.findById(categoryId);
        category.validateOwner(userId);
        category.update(request.getName());
        return categoryMapper.EntityToCategoryResponse(category);
    }

    public void delete(Long categoryId, Long userId) {
        Category category = categoryQueryService.findById(categoryId);
        category.validateOwner(userId);
        categoryRepository.delete(category);
    }
}
