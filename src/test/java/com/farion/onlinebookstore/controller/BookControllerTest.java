package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.TestObjectMother.BOOK_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.SEARCH_BY_TITLE;
import static com.farion.onlinebookstore.util.TestObjectMother.SLASH;
import static com.farion.onlinebookstore.util.TestObjectMother.TEST_BOOKS;
import static com.farion.onlinebookstore.util.TestObjectMother.getTestObjects;
import static com.farion.onlinebookstore.util.TestObjectMother.testCollectionEquality;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.book.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser
    @Test
    @DisplayName("Finding by id with existent id")
    void findById_validId_Success() throws Exception {
        List<BookDto> expected = getTestObjects(objectMapper, TEST_BOOKS, BookDto.class);
        int testId = 5;

        MvcResult result =
                mockMvc.perform(get(BOOK_ENDPOINT + SLASH + testId))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();

        BookDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);

        EqualsBuilder.reflectionEquals(expected.get(testId), actual);
    }

    @WithMockUser
    @Test
    @DisplayName("Finding by id with nonexistent id")
    void findById_invalidId_ThrownException() throws Exception {
        int invalidId = -3;

        mockMvc.perform(get(BOOK_ENDPOINT + SLASH + invalidId))
                .andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    @DisplayName("Find book by title")
    void findBookByTitle_validData_Success() throws Exception {
        List<BookDto> booksFromDB = getTestObjects(objectMapper, TEST_BOOKS, BookDto.class);
        int expectedId = 2;
        String testTitle = "1984";

        MvcResult result =
                mockMvc.perform(get(BOOK_ENDPOINT + SLASH + SEARCH_BY_TITLE + testTitle))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));

        EqualsBuilder.reflectionEquals(booksFromDB.get(expectedId), actual);
    }

    @WithMockUser
    @Test
    @DisplayName("Empty response because searching nonexistent book")
    void findBookByTitle_validData_EmptyResponse() throws Exception {
        String testTitle = "Microeconomic";

        MvcResult result = mockMvc.perform(get(
                        BOOK_ENDPOINT + SLASH + SEARCH_BY_TITLE + testTitle))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));

        assertTrue(actual.isEmpty());
    }

    @WithMockUser
    @Test
    @DisplayName("Return list of all books")
    void findAll_returnsListOfBooks() throws Exception {
        List<BookDto> expected = getTestObjects(objectMapper, TEST_BOOKS, BookDto.class);

        MvcResult result =
                mockMvc.perform(get(BOOK_ENDPOINT))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, BookDto.class));

        testCollectionEquality(expected, actual, actual.size());
    }
}
