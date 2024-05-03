package com.farion.onlinebookstore.util;

import com.farion.onlinebookstore.entity.Category;
import java.util.List;
import java.util.Set;

public class ConstUtil {
    public static final String URL_TEMPLATE = "/api/books";
    public static final String TEST_TITLE = "Cats Cradle";
    public static final String TEST_AUTHOR = "Kurt Vonnegut";
    public static final String TEST_ISBN = "9780061120084";
    public static final String TEST_DESCRIPTION = "The best of the best";
    public static final String TEST_CATEGORY_NAME = "Dystopia";
    public static final Long TEST_ID = 1L;
    public static final Set<Long> TEST_CATEGORY = Set.of(1L);
    public static final List<Category> TEST_CATEGORY_LIST = List.of(new Category(), new Category());

    private ConstUtil() {
        throw new RuntimeException("This constructor shouldn`t invoke");
    }
}
