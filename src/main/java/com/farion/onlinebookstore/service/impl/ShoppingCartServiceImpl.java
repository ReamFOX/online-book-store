package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.mapper.CartMapper;
import com.farion.onlinebookstore.repository.CartItemRepository;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Override
    public ShoppingCartDto getByUserEmail(String email) {
        return cartMapper.toDto(getCartByEmail(email));
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        updateCart(cart);
    }

    @Override
    public void clearShoppingCartByEmail(String email) {
        ShoppingCart cart = getCartByEmail(email);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.setCartItems(new HashSet<>());
        updateCart(cart);
    }

    @Override
    public void updateCart(ShoppingCart cart) {
        cartRepository.save(cart);
    }

    private ShoppingCart getCartByEmail(String email) {
        return cartRepository.findByUser_Email(email).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by "
                        + "user email: " + email));
    }
}
