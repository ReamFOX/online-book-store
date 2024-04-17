package com.farion.onlinebookstore.dto;

import com.farion.onlinebookstore.dto.item.CartItemDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
