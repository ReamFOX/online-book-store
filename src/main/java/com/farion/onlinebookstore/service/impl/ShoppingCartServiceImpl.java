package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.dto.book.BookDto;
import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.UpdateCartItemDto;
import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.mapper.ShoppingCartMapper;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.service.BookService;
import com.farion.onlinebookstore.service.CartItemService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final CartItemService cartItemService;
    private final BookService bookService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartDto getByUserId(Long id) {
        return shoppingCartMapper.toDto(getCartByUserId(id));
    }

    @Override
    public CartItemDto addToCart(CreateCartItemRequestDto requestDto, Long id) {
        ShoppingCart cart = getCartByUserId(id);
        BookDto bookDto = bookService.findById(requestDto.getBookId());
        Optional<CartItem> itemInCartOptional = cart.getCartItems().stream()
                .filter(item -> item.getBook().getTitle().equals(bookDto.getTitle()))
                .findFirst();
        if (itemInCartOptional.isPresent()) {
            return cartItemService.updateCartItemInCart(itemInCartOptional.get(), requestDto);
        }
        return cartItemService.createCartItem(requestDto, bookDto.getTitle(), cart);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        updateCart(cart);
    }

    @Transactional
    @Override
    public CartItemDto updateItemByUserId(Long itemId, Long userId, UpdateCartItemDto updateDto) {
        checkExistingInCart(itemId, userId);
        return cartItemService.updateByUserId(itemId, userId, updateDto);
    }

    @Transactional
    @Override
    public void deleteItemByUserId(Long itemId, Long userId) {
        checkExistingInCart(itemId, userId);
        cartItemService.deleteById(itemId);
    }

    @Override
    public void clearShoppingCartByUserId(Long id) {
        ShoppingCart cart = getCartByUserId(id);
        cartItemService.deleteAll(cart.getCartItems());
        cart.setCartItems(new HashSet<>());
        updateCart(cart);
    }

    @Override
    public void updateCart(ShoppingCart cart) {
        cartRepository.save(cart);
    }

    private ShoppingCart getCartByUserId(Long id) {
        return cartRepository.findByUser_Id(id).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by "
                        + "user id: " + id));
    }

    private void checkExistingInCart(Long itemId, Long userid) {
        Set<CartItem> cartItems = getCartByUserId(userid)
                .getCartItems();
        boolean isPresent = cartItems.stream()
                .anyMatch(item -> item.getId().equals(itemId));
        if (!isPresent) {
            throw new EntityNotFoundException("Item with id " + itemId
                    + " not in your shopping cart");
        }
    }
}
