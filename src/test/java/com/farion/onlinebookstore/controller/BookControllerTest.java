package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.ConstUtil.TEST_AUTHOR;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_CATEGORY;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_DESCRIPTION;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_ISBN;
import static com.farion.onlinebookstore.util.ConstUtil.TEST_TITLE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.book.BookDto;
import com.farion.onlinebookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new book")
    void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle(TEST_TITLE);
        requestDto.setAuthor(TEST_AUTHOR);
        requestDto.setIsbn(TEST_ISBN);
        requestDto.setPrice(BigDecimal.valueOf(120));
        requestDto.setCategories(TEST_CATEGORY);
        requestDto.setDescription(TEST_DESCRIPTION);
        requestDto.setCoverImage("tokillamockingbird.jpg");

        BookDto expectedBook = new BookDto();
        expectedBook.setTitle(TEST_TITLE);
        expectedBook.setAuthor(TEST_AUTHOR);
        expectedBook.setIsbn(TEST_ISBN);
        expectedBook.setPrice(BigDecimal.ONE);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    @WithMockUser(username = "testuser")
    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}

