package com.todo.cateogry.presentation;

import com.todo.auth.domain.Auth;
import com.todo.cateogry.application.dto.CategoryRequest;
import com.todo.cateogry.application.dto.CategoryResponse;
import com.todo.cateogry.application.service.CategoryCommandService;
import com.todo.cateogry.application.service.CategoryQueryService;
import com.todo.common.response.SuccessResponse;
import com.todo.common.session.LoginUser;
import com.todo.common.session.resolver.Login;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Auth
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryQueryService categoryQueryService;
    private final CategoryCommandService categoryCommandService;

    @PostMapping
    public SuccessResponse<CategoryResponse> createCategory(
            @Login LoginUser user,
            @RequestBody CategoryRequest request
    ) {
        return SuccessResponse.success(HttpStatus.CREATED, categoryCommandService.createCategory(user.getUserId(), request));
    }

    @GetMapping
    public SuccessResponse<List<CategoryResponse>> findCategories(@Login LoginUser user) {
        return SuccessResponse.success(HttpStatus.OK, categoryQueryService.findAllByUserId(user.getUserId()));
    }

    @PutMapping("/{id}")
    public SuccessResponse<CategoryResponse> updateCategory(
            @Login LoginUser user,
            @PathVariable("id") Long categoryId,
            @RequestBody CategoryRequest request) {
        return SuccessResponse.success(HttpStatus.OK,
                categoryCommandService.updateCategory(categoryId, user.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<Void> updateCategory(@Login LoginUser user, @PathVariable("id") Long categoryId) {
        categoryCommandService.deleteCategory(categoryId, user.getUserId());
        return SuccessResponse.success(HttpStatus.OK);
    }

}
