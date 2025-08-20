package com.todo.cateogry.application.service;

import static com.todo.common.exception.ErrorCode.CATEGORY_NOT_FOUND;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.cateogry.exception.CategoryException;
import com.todo.cateogry.application.mapper.CategoryMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> findAll(Long userId) {
        List<Category> categories = categoryRepository.findCategoriesByUserId(userId);

        List<CategoryResponse> res = new ArrayList<>();
        for (Category category : categories) {
            res.add(categoryMapper.EntityToCategoryResponse(category));
        }

        return res;
    }

    public Category findById(Long categoryId) {
        return categoryRepository
                .findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryException(CATEGORY_NOT_FOUND));
    }

    public Category findByIdAndUserId(Long categoryId, Long userId) {
        return categoryRepository.findCategoryByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryException(CATEGORY_NOT_FOUND));
    }
}
