package com.todo.cateogry.service;

import static org.mockito.Mockito.when;

import com.todo.cateogry.application.service.CategoryCommandService;
import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.application.dto.CategoryRequest;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.cateogry.application.mapper.CategoryMapper;
import com.todo.user.domain.User;
import com.todo.user.application.service.UserQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryCommandServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @Mock
    UserQueryService userQueryService;

    @Mock
    CategoryQueryService categoryQueryService;

    @InjectMocks
    CategoryCommandService categoryCommandService;

    User user;

    @BeforeEach
    void beforeEach() {
        this.user = User.builder()
                .id(1L)
                .build();
    }

    @Test
    @DisplayName("사용자는 카테고리를 생성할 수 있다.")
    void 카테고리_생성_성공() {
        //given
        Category category = Category.builder()
                .id(1L)
                .name("WORK")
                .user(user)
                .build();

        CategoryRequest req = new CategoryRequest("WORK");

        when(categoryMapper.categoryRequestToEntity(req)).thenReturn(category);
        when(categoryMapper.EntityToCategoryResponse(category)).thenReturn(new CategoryResponse("WORK"));

        //when
        CategoryResponse res = categoryCommandService.create(user.getId(), req);

        //then
        Assertions.assertThat(res.getName()).isEqualTo(req.getName());
    }

    @Test
    @DisplayName("사용자는 카테고리를 수정할 수 있다.")
    void 카테고리_수정_성공() {
        //given
        Category category = Category.builder()
                .id(1L)
                .name("WORK")
                .user(user)
                .build();

        CategoryRequest req = new CategoryRequest("EDIT");

        when(categoryQueryService.findCategoryByCategoryIdAndUserId(1L, 1L)).thenReturn(category);
        when(categoryMapper.EntityToCategoryResponse(category)).thenReturn(new CategoryResponse("EDIT"));

        //when
        CategoryResponse res = categoryCommandService.update(1L, user.getId(), req);

        //then
        Assertions.assertThat(res.getName()).isEqualTo(req.getName());
    }

}