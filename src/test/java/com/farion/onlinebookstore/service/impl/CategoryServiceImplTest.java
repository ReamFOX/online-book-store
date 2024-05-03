package com.farion.onlinebookstore.service.impl;

import static com.farion.onlinebookstore.util.ConstUtil.TEST_CATEGORY_LIST;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_CATEGORY_NAME;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_DESCRIPTION;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_ID;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.farion.onlinebookstore.dto.category.CategoryDto;
import com.farion.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.farion.onlinebookstore.entity.Category;
import com.farion.onlinebookstore.mapper.CategoryMapper;
import com.farion.onlinebookstore.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAll_ReturnsListOfCategories() {
        int expectedSize = 2;
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(TEST_CATEGORY_LIST));

        List<CategoryDto> actual = categoryService.findAll(Pageable.unpaged());

        assertNotNull(actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    void getById_WithValidId_ReturnsCategoryDto() {
        Category category = new Category();
        category.setId(TEST_ID);
        category.setName(TEST_TITLE);
        CategoryDto categoryDto = new CategoryDto(TEST_ID, TEST_CATEGORY_NAME, TEST_DESCRIPTION);
        when(categoryRepository.findById(TEST_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.getById(TEST_ID);

        assertNotNull(actual);
        assertEquals(TEST_ID, actual.id());
    }

    @Test
    void getById_WithInvalidId_ThrowsEntityNotFoundException() {
        String expectedMessage = "Can`t find category by id: " + TEST_ID;
        when(categoryRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(TEST_ID));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createCategory_ReturnsCreatedCategoryDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        Category category = new Category();
        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        CategoryDto expectedDto = new CategoryDto(TEST_ID, TEST_CATEGORY_NAME, TEST_DESCRIPTION);
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryDto result = categoryService.createCategory(requestDto);

        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void update_WithValidId_ReturnsUpdatedCategoryDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        Category category = new Category();
        category.setId(TEST_ID);
        when(categoryRepository.findById(TEST_ID)).thenReturn(Optional.of(category));
        Category updatedCategory = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        CategoryDto expectedDto = new CategoryDto(TEST_ID, TEST_CATEGORY_NAME, TEST_DESCRIPTION);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(expectedDto);

        CategoryDto result = categoryService.update(TEST_ID, requestDto);

        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void update_WithInvalidId_ThrowsEntityNotFoundException() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        String expectedMessage = "Can`t find category by id: " + TEST_ID;
        when(categoryRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(TEST_ID, requestDto));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void deleteById_WithValidId_VerifiesRepositoryDeleteById() {
        categoryService.deleteById(TEST_ID);

        verify(categoryRepository, times(1)).deleteById(TEST_ID);
    }
}
