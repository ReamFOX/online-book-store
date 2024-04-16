package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Category;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.id FROM Category c")
    Set<Long> findAllIds();
}
