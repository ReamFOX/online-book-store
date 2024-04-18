package com.farion.onlinebookstore.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemDto {
    @NotNull
    @Min(value = 1, message = "Quantity can't be 0 or less")
    private int quantity;
}
