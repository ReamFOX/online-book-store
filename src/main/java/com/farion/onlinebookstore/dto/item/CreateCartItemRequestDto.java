package com.farion.onlinebookstore.dto.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotNull
    private Long bookId;
    @NotNull
    @Positive(message = "Quantity can't be 0 or less")
    private int quantity;
}
