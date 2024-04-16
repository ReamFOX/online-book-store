package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;

public interface ShoppingCartService {
    ShoppingCart getByUserEmail(String email);

    void registerNewShoppingCart(User user);

    void clearShoppingCart(ShoppingCart cart);
}
