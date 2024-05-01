package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.dto.item.cartitem.CartItemDto;
import com.farion.onlinebookstore.dto.item.cartitem.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.cartitem.UpdateCartItemDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;

public interface ShoppingCartService {

    CartItemDto addToCart(CreateCartItemRequestDto requestDto, Long id);

    ShoppingCartDto getByUserId(Long id);

    void registerNewShoppingCart(User user);

    CartItemDto updateItemByUserId(Long itemId, Long userId, UpdateCartItemDto updateDto);

    void deleteItemByUserId(Long itemId, Long userId);

    void clearShoppingCartByUserId(Long id);

    void updateCart(ShoppingCart cart);
}
