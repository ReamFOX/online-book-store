package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.dto.BookDto;
import com.farion.onlinebookstore.entity.Book;

public interface BookMapper {
    BookDto toDto(Book book);
}
