package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.config.MapperConfig;
import com.farion.onlinebookstore.dto.book.BookDto;
import com.farion.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.farion.onlinebookstore.dto.book.CreateBookRequestDto;
import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.entity.Category;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categories = book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categories);
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget Book book, CreateBookRequestDto requestDto) {
        Set<Category> categories = requestDto.getCategories()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        return Optional.ofNullable(id)
                .map(Book::new)
                .orElse(null);
    }
}

