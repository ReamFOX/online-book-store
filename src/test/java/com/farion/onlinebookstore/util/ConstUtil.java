package com.farion.onlinebookstore.util;

import com.farion.onlinebookstore.entity.Category;
import java.io.File;
import java.util.List;
import java.util.Set;

public class ConstUtil {
    public static final String TEST_TITLE = "Cats Cradle";
    public static final String TEST_DESCRIPTION = "The best of the best";
    public static final String TEST_CATEGORY_NAME = "Dystopia";
    public static final String BOOK_ENDPOINT = "/books";
    public static final String CATEGORY_ENDPOINT = "/categories";
    public static final String AUTH_ENDPOINT = "/auth";
    public static final String REGISTRATION_ENDPOINT = "/register";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String CART_ENDPOINT = "/cart";
    public static final String CLEAR_ENDPOINT = "/clear";
    public static final String SLASH = "/";
    public static final String SEARCH_BY_TITLE = "/search?title=";
    public static final File TEST_BOOKS =
            new File("src/test/resources/data/test-books.json");
    public static final File TEST_CATEGORIES =
            new File("src/test/resources/data/test-categories.json");
    public static final Long TEST_ID = 1L;
    public static final Set<Long> TEST_CATEGORY = Set.of(1L);
    public static final List<Category> TEST_CATEGORY_LIST = List.of(new Category(), new Category());

    private ConstUtil() {
        throw new RuntimeException("This constructor shouldn`t invoke");
    }
}
