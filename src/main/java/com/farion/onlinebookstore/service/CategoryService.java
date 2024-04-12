package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.category.CategoryDto;
import com.farion.onlinebookstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto createCategory(CreateCategoryRequestDto requestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);

    Set<Long> getAllCategoryIds();
}
