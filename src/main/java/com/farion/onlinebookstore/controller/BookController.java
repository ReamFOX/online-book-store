package com.farion.onlinebookstore.controller;

import com.farion.onlinebookstore.dto.BookDto;
import com.farion.onlinebookstore.dto.BookSearchParameters;
import com.farion.onlinebookstore.dto.CreateBookRequestDto;
import com.farion.onlinebookstore.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto save(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.createBook(requestDto);
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        return bookService.search(params, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookDto updateById(@PathVariable Long id, @RequestBody CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }
}
