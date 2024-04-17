package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.mapper.CartItemMapper;
import com.farion.onlinebookstore.mapper.CartMapper;
import com.farion.onlinebookstore.repository.CartItemRepository;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getByUserEmail(String email) {
        ShoppingCart cart = cartRepository.findByUser_Email(email).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by "
                        + "user email: " + email));
        Set<CartItemDto> cartItemDtos =
                cart.getCartItems().stream().map(cartItemMapper::toDto).collect(Collectors.toSet());
        ShoppingCartDto cartDto = cartMapper.toDto(cart);
        cartDto.setCartItems(cartItemDtos);
        return cartDto;
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
                new EntityNotFoundException("Can`t find shopping cart by "
                        + "user email: " + email));
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.setCartItems(new HashSet<>());
        cartRepository.save(cart);
    }

    @Override
    public void updateCart(ShoppingCart cart) {
        cartRepository.save(cart);
    }
}
