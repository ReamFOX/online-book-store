package com.farion.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(max = 255, message = "Category name can`t be longer than 255 characters")
    private String name;
    @Size(max = 255, message = "Category description can`t be longer than 255 characters")
    private String description;
}
