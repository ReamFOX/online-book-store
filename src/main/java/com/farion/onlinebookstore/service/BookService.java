package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.BookDto;
import com.farion.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll();
}
