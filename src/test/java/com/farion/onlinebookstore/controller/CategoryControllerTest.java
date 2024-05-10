package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.TestObjectMother.CATEGORY_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.SLASH;
import static com.farion.onlinebookstore.util.TestObjectMother.TEST_CATEGORIES;
import static com.farion.onlinebookstore.util.TestObjectMother.getTestObjects;
import static com.farion.onlinebookstore.util.TestObjectMother.testCollectionEquality;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.category.CategoryDto;
import com.farion.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
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

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(scripts = "classpath:db/categories/remove-horror-from-category-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create a new category with valid data")
    void createCategory_validInput_success() throws Exception {
        Long expectedId = 9L;
        String expectedName = "Horror";
        String expectedDescription =
                "What unites the books in this genre "
                        + "is not theme, plot, or setting, "
                        + "but the feeling they inspire in the reader.";
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName(expectedName);
        requestDto.setDescription(expectedDescription);

        CategoryDto expected = new CategoryDto(expectedId, expectedName, expectedDescription);

        MvcResult result = mockMvc.perform(
                        post(CATEGORY_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Throws an exception because request of category is null")
    void createCategory_nullName_throwException() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();

        mockMvc.perform(post(CATEGORY_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    @DisplayName("Finding by id with existent id")
    void findById_validId_Success() throws Exception {
        List<CategoryDto> expected =
                getTestObjects(objectMapper, TEST_CATEGORIES, CategoryDto.class);
        int testId = 5;

        MvcResult result = mockMvc.perform(get(CATEGORY_ENDPOINT + SLASH + testId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        EqualsBuilder.reflectionEquals(expected.get(testId), actual);
    }

    @WithMockUser
    @Test
    @DisplayName("Finding by id with nonexistent id")
    void findById_invalidId_ThrownException() throws Exception {
        int invalidId = -3;

        mockMvc.perform(get(CATEGORY_ENDPOINT + SLASH + invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Return list of all categories")
    void findAll_returnsListOfCategories() throws Exception {
        List<CategoryDto> expected =
                getTestObjects(objectMapper, TEST_CATEGORIES, CategoryDto.class);
        MvcResult result = mockMvc.perform(get(CATEGORY_ENDPOINT))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, CategoryDto.class)
        );

        testCollectionEquality(expected, actual, actual.size());
    }
}
