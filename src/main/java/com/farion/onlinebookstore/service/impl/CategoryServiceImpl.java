package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.mapper.CategoryMapper;
import com.farion.onlinebookstore.repository.CategoryRepository;
import com.farion.onlinebookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
}
