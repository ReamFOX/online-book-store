package com.farion.onlinebookstore.mapper.impl;

import com.farion.onlinebookstore.dto.BookDto;
import com.farion.onlinebookstore.dto.CreateBookRequestDto;
import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.mapper.BookMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        com.farion.onlinebookstore.dto.BookDto bookDto = new com.farion.onlinebookstore.dto.BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        return bookDto;
    }

    @Override
    public Book toModel(CreateBookRequestDto requestDto) {
        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setPrice(requestDto.getPrice());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());
        return book;
    }


}
