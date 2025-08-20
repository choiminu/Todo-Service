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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10})
    @DisplayName("사용자는 자신이 생성한 모든 카테고리를 조회할 수 있다.")
    void findAllByUserId(int size) {
        //given
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            categories.add(Category.builder().build());
        }

        when(categoryRepository.findCategoriesByUserId(user.getId())).thenReturn(categories);

        //when
        List<CategoryResponse> responses = categoryQueryService.findAllByUserId(user.getId());

        //then
        Assertions.assertThat(responses.size()).isEqualTo(size);

    }

}