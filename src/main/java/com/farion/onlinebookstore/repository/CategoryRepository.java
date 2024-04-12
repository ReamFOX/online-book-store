package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
