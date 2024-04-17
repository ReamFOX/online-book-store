package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;
import com.farion.onlinebookstore.entity.Book;
import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.mapper.CartItemMapper;
import com.farion.onlinebookstore.mapper.CartMapper;
import com.farion.onlinebookstore.repository.CartItemRepository;
import com.farion.onlinebookstore.repository.book.BookRepository;
import com.farion.onlinebookstore.service.CartItemService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository itemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartService shoppingCartService;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;

    @Override
    public CartItemDto save(CreateCartItemRequestDto requestDto, String email) {
        ShoppingCart cart = cartMapper.toEntity(shoppingCartService.getByUserEmail(email));
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Book with id "
                        + requestDto.getBookId() + " doesn't exist"));
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.getBook().setTitle(book.getTitle());
        cart.getCartItems().add(cartItem);
        shoppingCartService.updateCart(cart);
        cartItem.setShoppingCart(cart);
        return cartItemMapper.toDto(itemRepository.save(cartItem));
    }
}
