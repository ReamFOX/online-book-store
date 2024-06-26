package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.book.BookDto;
import com.farion.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.farion.onlinebookstore.dto.book.BookSearchParameters;
import com.farion.onlinebookstore.dto.book.CreateBookRequestDto;
import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.exception.InvalidCategoryIdsException;
import com.farion.onlinebookstore.mapper.BookMapper;
import com.farion.onlinebookstore.repository.book.BookRepository;
import com.farion.onlinebookstore.repository.book.BookSpecificationBuilder;
import com.farion.onlinebookstore.service.BookService;
import com.farion.onlinebookstore.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto createBook(CreateBookRequestDto requestDto) {
        Set<Long> invalidCategoryIds = getInvalidCategoryIds(requestDto.getCategories());
        if (!invalidCategoryIds.isEmpty()) {
            throw new InvalidCategoryIdsException("Invalid category ids: " + invalidCategoryIds);
        }
        Book book = bookMapper.toModel(requestDto);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + id + " doesn't exist"));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + id + " doesn't exist"));
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setPrice(requestDto.getPrice());
        book.setIsbn(requestDto.getIsbn());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategory(Long id) {
        return bookRepository.findByCategories_Id(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private Set<Long> getInvalidCategoryIds(Set<Long> categoryIds) {
        Set<Long> allCategoryIds = categoryService.getAllCategoryIds();
        Set<Long> invalidCategoryIds = new HashSet<>();
        categoryIds.forEach(id -> {
            if (!allCategoryIds.contains(id)) {
                invalidCategoryIds.add(id);
            }
        });
        return invalidCategoryIds;
    }
}
