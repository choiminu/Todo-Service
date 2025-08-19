package com.todo.cateogry.domain.repository;

import com.todo.cateogry.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
