package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.mapper.CartMapper;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.repository.UserRepository;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    public ShoppingCartDto getByUserEmail(String email) {
        return cartMapper.toDto(cartRepository.findByUser_Email(email).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by " +
                        "user email: " + email)));
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    @Override
    public void clearShoppingCart(String email) {
        ShoppingCart cart = cartRepository.findByUser_Email(email).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by " +
                        "user email: " + email));
        cart.setCartItems(new HashSet<>());
        cartRepository.save(cart);
    }
}
