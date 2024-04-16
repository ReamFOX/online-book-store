package com.farion.onlinebookstore.dto.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateBookRequestDto {
    @NotBlank
    @Size(max = 255, message = "Title can`t be longer than 255 characters")
    private String title;
    @NotBlank
    @Size(max = 255, message = "Author can`t be longer than 255 characters")
    private String author;
    @NotBlank
    @ISBN(message = "invalid isbn format")
    private String isbn;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false, message = "Price can't be 0 or less")
    private BigDecimal price;
    @Size(max = 255, message = "Book description can`t be longer than 255 characters")
    private String description;
    @URL(message = "invalid url")
    @Size(max = 255, message = "URL for cover image can`t be longer than 255 characters")
    private String coverImage;
    @NotEmpty(message = "Category can not be empty")
    @Size(min = 1, message = "Book must have at least 1 category")
    private Set<Long> categories;
}
