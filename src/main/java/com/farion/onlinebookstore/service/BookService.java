package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.BookDto;
import com.farion.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto getBookById(Long id);

    List<BookDto> findAll();
}
