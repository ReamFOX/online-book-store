package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.config.MapperConfig;
import com.farion.onlinebookstore.dto.category.CategoryDto;
import com.farion.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.farion.onlinebookstore.entity.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);
}
