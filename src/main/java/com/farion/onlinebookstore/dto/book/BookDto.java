package com.farion.onlinebookstore.dto.book;

import com.farion.onlinebookstore.entity.Category;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Set<Category> categories;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
