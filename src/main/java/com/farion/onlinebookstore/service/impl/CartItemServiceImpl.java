package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.item.cartitem.CartItemDto;
import com.farion.onlinebookstore.dto.item.cartitem.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.cartitem.UpdateCartItemDto;
import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.mapper.CartItemMapper;
import com.farion.onlinebookstore.repository.CartItemRepository;
import com.farion.onlinebookstore.service.CartItemService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository itemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto createCartItem(CreateCartItemRequestDto requestDto,
                                      String bookTitle,
                                      ShoppingCart cart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.getBook().setTitle(bookTitle);
        cartItem.setShoppingCart(cart);
        return cartItemMapper.toDto(itemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItemInCart(CartItem itemInCart,
                                            CreateCartItemRequestDto requestDto) {
        itemInCart.setQuantity(itemInCart.getQuantity() + requestDto.getQuantity());
        return cartItemMapper.toDto(itemRepository.save(itemInCart));
    }

    @Override
    public CartItemDto updateByUserId(Long itemId, Long userId, UpdateCartItemDto updateDto) {
        CartItem itemFromDB = itemRepository.findById(itemId).get();
        itemFromDB.setQuantity(updateDto.getQuantity());
        return cartItemMapper.toDto(itemRepository.save(itemFromDB));
    }

    @Override
    public void deleteById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public void deleteAll(Set<CartItem> cartItems) {
        itemRepository.deleteAll(cartItems);
    }
}
