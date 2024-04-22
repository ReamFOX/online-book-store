package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.UpdateCartItemDto;
import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import java.util.Set;

public interface CartItemService {
    CartItemDto createCartItem(CreateCartItemRequestDto requestDto,
                               String bookTitle,
                               ShoppingCart cart);

    CartItemDto updateCartItemInCart(CartItem itemInCartOptional,
                                     CreateCartItemRequestDto requestDto);

    CartItemDto updateByUserId(Long itemId, Long userId, UpdateCartItemDto updateDto);

    void deleteById(Long itemId);

    void deleteAll(Set<CartItem> cartItems);
}
