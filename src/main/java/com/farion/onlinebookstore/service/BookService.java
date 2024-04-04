package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.BookDto;
import com.farion.onlinebookstore.dto.BookSearchParameters;
import com.farion.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParameters params, Pageable pageable);
}
