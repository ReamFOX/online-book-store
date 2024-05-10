package com.farion.onlinebookstore.util;

import java.util.List;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

public class EqualityUtil {

    private EqualityUtil() {
    }

    public static <T> void testCollectionEquality(List<T> expected, List<T> actual, int size) {
        for (int i = 0; i < size; i++) {
            EqualsBuilder.reflectionEquals(expected.get(i), actual.get(i));
        }
    }
}
