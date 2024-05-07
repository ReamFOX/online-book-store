package com.farion.onlinebookstore.service.impl;

import static com.farion.onlinebookstore.util.ConstUtil.TEST_CATEGORY;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_ID;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_TITLE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.farion.onlinebookstore.dto.book.BookDto;
import com.farion.onlinebookstore.dto.book.CreateBookRequestDto;
import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.exception.InvalidCategoryIdsException;
import com.farion.onlinebookstore.mapper.BookMapper;
import com.farion.onlinebookstore.repository.book.BookRepository;
import com.farion.onlinebookstore.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Saving book with valid data")
    @Test
    void testCreateBook_ValidInput() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle(TEST_TITLE);
        requestDto.setCategories(TEST_CATEGORY);

        Book book = new Book();
        book.setTitle(TEST_TITLE);

        BookDto bookDto = new BookDto();
        bookDto.setTitle(TEST_TITLE);
        bookDto.setCategoryIds(TEST_CATEGORY);

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(categoryService.getAllCategoryIds()).thenReturn(TEST_CATEGORY);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.createBook(requestDto);

        assertNotNull(result);
        assertEquals(requestDto.getTitle(), result.getTitle());
    }

    @DisplayName("Throw exception because of book with nonexistent id")
    @Test
    void testCreateBook_InvalidCategories() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        requestDto.setCategories(TEST_CATEGORY);
        String expectedMessage = "Invalid category ids: " + TEST_CATEGORY;

        when(categoryService.getAllCategoryIds()).thenReturn(Collections.emptySet());

        Exception exception = assertThrows(InvalidCategoryIdsException.class,
                () -> bookService.createBook(requestDto));
        assertEquals(expectedMessage, exception.getMessage());

    }

    @DisplayName("Finding by id with existent id")
    @Test
    void testFindById_ExistingId() {
        Book book = new Book();
        book.setId(TEST_ID);

        BookDto expected = new BookDto();
        expected.setId(TEST_ID);

        when(bookRepository.findById(TEST_ID)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto result = bookService.findById(TEST_ID);

        assertNotNull(result);
        assertEquals(expected.getTitle(), result.getTitle());
    }

    @DisplayName("Finding by id with nonexistent id")
    @Test
    void testFindById_NonExistingId() {
        String expectedMessage = "Book with id " + TEST_ID + " doesn't exist";

        when(bookRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(TEST_ID));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Deleting with existing id")
    void testDeleteById_ExistingId() {
        assertDoesNotThrow(() -> bookService.deleteById(TEST_ID));
        verify(bookRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    @DisplayName("Return list of all books")
    void testFindAll() {
        List<Book> twoBooks = Arrays.asList(new Book(), new Book());

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(twoBooks));
        when(bookMapper.toDto(any())).thenReturn(new BookDto());

        List<BookDto> result = bookService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(twoBooks.size(), result.size());
    }
}
