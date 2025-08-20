package com.todo.cateogry.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.cateogry.application.mapper.CategoryMapper;
import com.todo.user.domain.User;
import com.todo.user.application.service.UserCommandService;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryQueryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @Mock
    UserCommandService userCommandService;

    @InjectMocks
    CategoryQueryService categoryQueryService;

    User user;

    @BeforeEach
    void beforeEach() {
        this.user = User.builder()
                .id(1L)
                .build();
    }

    @Test
    @DisplayName("사용자는 자신이 생성한 모든 카테고리를 조회할 수 있다.")
    void 카테고리_조회_성공() {
        //given
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            categories.add(Category.builder().id((long) i).name("title" + i).user(user).build());
        }
        when(categoryMapper.EntityToCategoryResponse(any())).thenReturn(new CategoryResponse(1L, "WORK"));
        when(categoryRepository.findCategoriesByUserId(1L)).thenReturn(categories);

        //when
        List<CategoryResponse> responses = categoryQueryService.findAllByUserId(user.getId());

        //then
        Assertions.assertThat(responses.size()).isEqualTo(10);
    }

}