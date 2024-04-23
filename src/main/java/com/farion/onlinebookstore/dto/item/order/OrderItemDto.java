package com.farion.onlinebookstore.dto.item.order;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
