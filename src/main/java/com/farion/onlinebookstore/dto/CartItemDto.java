package com.farion.onlinebookstore.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long bookId;
    private int quantity;
}
