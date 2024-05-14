package com.farion.onlinebookstore.util;

import com.farion.onlinebookstore.entity.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class TestObjectMother {
    public static final String TEST_TITLE = "Cats Cradle";
    public static final String TEST_DESCRIPTION = "The best of the best";
    public static final String TEST_CATEGORY_NAME = "Dystopia";
    public static final String BOOK_ENDPOINT = "/books";
    public static final String CATEGORY_ENDPOINT = "/categories";
    public static final String SLASH = "/";
    public static final String SEARCH_BY_TITLE = "/search?title=";
    public static final Long TEST_ID = 1L;
    public static final Set<Long> TEST_CATEGORY = Set.of(1L);
    public static final List<Category> TEST_CATEGORY_LIST = List.of(new Category(), new Category());
    public static final File TEST_BOOKS =
            new File("src/test/resources/data/test-books.json");
    public static final File TEST_CATEGORIES =
            new File("src/test/resources/data/test-categories.json");

    private TestObjectMother() {
    }

    public static <T> List<T> getTestObjects(
            ObjectMapper objectMapper,
            File testFile,
            Class<T> clazz
    ) throws IOException {
        return objectMapper.readValue(
                testFile,
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, clazz));
    }
}
