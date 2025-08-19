package com.todo.cateogry.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.dto.CategoryRequest;
import com.todo.cateogry.dto.CategoryResponse;
import com.todo.cateogry.mapper.CategoryMapper;
import com.todo.user.domain.User;
import com.todo.user.service.UserDomainService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
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
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("ex"));
    }
}
