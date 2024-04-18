package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;

public interface ShoppingCartService {
    ShoppingCartDto getByUserEmail(String email);

    void registerNewShoppingCart(User user);

    void clearShoppingCartByEmail(String email);

    void updateCart(ShoppingCart cart);
}
