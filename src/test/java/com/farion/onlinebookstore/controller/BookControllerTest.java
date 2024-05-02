package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.ConstUtil.CONTENT_TYPE;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_TITLE;
import static com.farion.onlinebookstore.util.ConstUtil.URL_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.book.BookDto;
import com.farion.onlinebookstore.dto.book.CreateBookRequestDto;
import com.farion.onlinebookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;


    @Test
    void save() throws Exception {
        BookDto expectedBook = new BookDto();
        expectedBook.setTitle(TEST_TITLE);
        String content = "{\"title\": \"Test param\"}";

        when(bookService.createBook(any(CreateBookRequestDto.class))).thenReturn(expectedBook);

        MvcResult result = mockMvc.perform(post(URL_TEMPLATE)
                .contentType(CONTENT_TYPE)
                .content(content))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString();
        assertEquals(expectedBook.getTitle(), actual);
    }
//
//    @Test
//    void findById() {
//        BookDto expectedBook = new BookDto();
//        when(bookService.findById(TEST_ID)).thenReturn(expectedBook);
//
//        BookDto foundBook = bookController.findById(TEST_ID);
//        assertEquals(expectedBook, foundBook);
//    }
//
//    @Test
//    void findAll() {
//        Pageable pageable = Pageable.unpaged();
//        List<BookDto> bookDtoList = new ArrayList<>();
//        when(bookService.findAll(pageable)).thenReturn(bookDtoList);
//
//        List<BookDto> allBooks = bookController.findAll(pageable);
//        assertEquals(bookDtoList, allBooks);
//    }
//
//    @Test
//    void search() {
//        BookSearchParameters params = new BookSearchParameters(
//                TEST_SEARCH_PARAM,
//                TEST_SEARCH_PARAM,
//                TEST_SEARCH_PARAM
//        );
//        Pageable pageable = Pageable.unpaged();
//        List<BookDto> bookDtoList = new ArrayList<>();
//        when(bookService.search(params, pageable)).thenReturn(bookDtoList);
//
//        List<BookDto> searchedBooks = bookController.search(params, pageable);
//        assertEquals(bookDtoList, searchedBooks);
//    }
//
//    @Test
//    void delete() {;
//        doNothing().when(bookService).deleteById(TEST_ID);
//        bookController.delete(TEST_ID);
//        verify(bookService, times(1)).deleteById(TEST_ID);
//    }
//
//    @Test
//    void updateById() {
//        CreateBookRequestDto requestDto = new CreateBookRequestDto();
//        BookDto expectedBook = new BookDto();
//        when(bookService.updateById(TEST_ID, requestDto)).thenReturn(expectedBook);
//
//        BookDto updatedBook = bookController.updateById(TEST_ID, requestDto);
//        assertEquals(expectedBook, updatedBook);
//    }
}

