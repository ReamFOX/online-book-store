package com.farion.onlinebookstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateBookRequestDto {
    @NotEmpty
    @Size(max = 255, message = "can`t be longer than 255 characters")
    private String title;
    @NotEmpty
    @Size(max = 255, message = "can`t be longer than 255 characters")
    private String author;
    @ISBN(message = "invalid format")
    private String isbn;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false, message = "can't be 0 or less")
    private BigDecimal price;
    @Size(max = 255, message = "can`t be longer than 255 characters")
    private String description;
    @URL(message = "invalid url")
    @Size(max = 255, message = "can`t be longer than 255 characters")
    private String coverImage;
}
