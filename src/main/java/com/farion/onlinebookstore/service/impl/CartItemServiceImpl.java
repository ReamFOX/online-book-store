package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.repository.CartItemRepository;
import com.farion.onlinebookstore.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository itemRepository;

    @Override
    public CartItem save(CartItem cartItem) {
        return itemRepository.save(cartItem);
    }
}
