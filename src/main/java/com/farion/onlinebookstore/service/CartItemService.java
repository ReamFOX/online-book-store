package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;

public interface CartItemService {
    CartItemDto save(CreateCartItemRequestDto requestDto, String email);
}
