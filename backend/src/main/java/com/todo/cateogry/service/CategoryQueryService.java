package com.todo.cateogry.service;

import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.dto.CategoryRequest;
import com.todo.cateogry.dto.CategoryResponse;
import com.todo.cateogry.mapper.CategoryMapper;
import com.todo.user.domain.User;
import com.todo.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;
    private final UserDomainService userDomainService;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(Long userId, CategoryRequest request) {
        User findUser = userDomainService.findUserById(userId);

        Category category = categoryMapper.categoryRequestToEntity(request);
        category.setUser(findUser);

        categoryRepository.save(category);
        return categoryMapper.EntityToCategoryResponse(category);
    }

}
