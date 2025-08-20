package com.todo.cateogry.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.todo.cateogry.application.service.CategoryCommandService;
import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.cateogry.domain.Category;
import com.todo.cateogry.domain.repository.CategoryRepository;
import com.todo.cateogry.application.dto.CategoryRequest;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.cateogry.application.mapper.CategoryMapper;
import com.todo.cateogry.exception.CategoryException;
import com.todo.common.exception.ErrorCode;
import com.todo.user.domain.User;
import com.todo.user.application.service.UserQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "1, WORK",
            "2, LIFE",
            "3, DEV"
    })
    @DisplayName("사용자는 카테고리를 생성할 수 있다.")
    void createCategory_success(Long categoryId, String categoryName) {
        //given
        Category category = Category.builder()
                .id(categoryId)
                .name(categoryName)
                .user(user)
                .build();

        CategoryRequest req = new CategoryRequest(categoryName);
        when(categoryMapper.categoryRequestToEntity(req)).thenReturn(category);
        when(categoryMapper.EntityToCategoryResponse(category)).thenReturn(new CategoryResponse(categoryId,categoryName));

        //when
        CategoryResponse res = categoryCommandService.createCategory(user.getId(), req);

        //then
        assertThat(res.getName()).isEqualTo(categoryName);
        assertThat(res.getId()).isEqualTo(categoryId);
    }

    @ParameterizedTest
    @CsvSource({
            "1, WORK, DEV",
            "2, LIFE, WORK"
    })
    @DisplayName("사용자는 카테고리를 수정할 수 있다.")
    void updateCategory_success(Long categoryId, String before, String after) {

        //given
        Category category = Category.builder()
                .id(categoryId)
                .name(before)
                .user(user)
                .build();

        CategoryRequest req = new CategoryRequest(after);

        when(categoryQueryService.findCategoryByCategoryIdAndUserId(any(), any())).thenReturn(category);
        when(categoryMapper.EntityToCategoryResponse(category)).thenReturn(new CategoryResponse(categoryId, after));

        //when
        CategoryResponse res = categoryCommandService.updateCategory(categoryId, user.getId(), req);

        //then
        assertThat(res.getId()).isEqualTo(categoryId);
        assertThat(res.getName()).isEqualTo(after);
        assertThat(res.getName()).isNotEqualTo(before);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1,",
            "2, 2"
    })
    @DisplayName("사용자는 자신의 카테고리를 삭제할 수 있다.")
    void deleteCategory_success(Long categoryId, Long userId) {
        //given
        doNothing()
                .doThrow(new CategoryException(ErrorCode.CATEGORY_NOT_FOUND))
                .when(categoryRepository).delete(any());
        //when
        categoryCommandService.deleteCategory(categoryId, userId);

        //then
        Assertions.assertThatThrownBy(() -> categoryCommandService.deleteCategory(categoryId, userId))
                .isInstanceOf(CategoryException.class)
                .hasMessage(ErrorCode.CATEGORY_NOT_FOUND.getMessage());
    }

}