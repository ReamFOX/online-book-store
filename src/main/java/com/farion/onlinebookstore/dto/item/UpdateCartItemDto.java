package com.farion.onlinebookstore.dto.item;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemDto {
    @Min(value = 1, message = "Quantity can't be 0 or less")
    private int quantity;
}
