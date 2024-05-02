package com.farion.onlinebookstore.util;

import java.util.Set;

public class ConstUtil {
    public static final String URL_TEMPLATE = "/api/books";
    public static final String CONTENT_TYPE = "application/json";
    public static final String TEST_TITLE = "Test title";
    public static final Long TEST_ID = 1L;
    public static final Set<Long> TEST_CATEGORY = Set.of(1L);

    private ConstUtil() {
        throw new RuntimeException("This constructor shouldn`t invoke");
    }
}
