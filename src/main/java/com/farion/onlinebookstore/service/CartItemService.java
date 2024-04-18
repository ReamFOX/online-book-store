package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.UpdateCartItemDto;

public interface CartItemService {
    CartItemDto saveByEmail(CreateCartItemRequestDto requestDto, String email);

    CartItemDto updateByEmail(Long id, String email, UpdateCartItemDto updateDto);

    void deleteByEmail(Long id, String email);
}
