package com.todo.cateogry.domain.repository;

import com.todo.cateogry.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId")
    List<Category> findCategoriesByUserId(Long userId);

    @Query("SELECT c FROM Category c WHERE c.id = : id")
    Optional<Category> findCategoryById(Long id);
}
