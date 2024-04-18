package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.UpdateCartItemDto;
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
import java.util.Optional;
import java.util.Set;
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
    public CartItemDto saveByEmail(CreateCartItemRequestDto requestDto, String email) {
        ShoppingCart cart = cartMapper.toEntity(shoppingCartService.getByUserEmail(email));
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Book with id "
                        + requestDto.getBookId() + " doesn't exist"));
        Optional<CartItem> itemInCartOptional = cart.getCartItems().stream()
                .filter(item -> item.getBook().getTitle().equals(book.getTitle()))
                .findFirst();
        if (itemInCartOptional.isPresent()) {
            CartItem itemInCart = itemInCartOptional.get();
            itemInCart.setQuantity(itemInCart.getQuantity() + requestDto.getQuantity());
            itemInCart.setShoppingCart(cart);
            return cartItemMapper.toDto(itemRepository.save(itemInCart));
        }
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.getBook().setTitle(book.getTitle());
        cartItem.setShoppingCart(cart);
        return cartItemMapper.toDto(itemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateByEmail(Long id, String email, UpdateCartItemDto updateDto) {
        checkExistingInCart(id, email);
        CartItem itemFromDB = itemRepository.findById(id).get();
        itemFromDB.setQuantity(updateDto.getQuantity());
        return cartItemMapper.toDto(itemRepository.save(itemFromDB));
    }

    @Override
    public void deleteByEmail(Long id, String email) {
        checkExistingInCart(id, email);
        CartItem removableItem = itemRepository.findById(id).get();
        itemRepository.deleteById(id);
    }

    private void checkExistingInCart(Long id, String email) {
        Set<CartItem> cartItems = cartMapper
                .toEntity(shoppingCartService.getByUserEmail(email))
                .getCartItems();
        boolean isPresent = cartItems.stream()
                .anyMatch(item -> item.getId().equals(id));
        if (!isPresent) {
            throw new EntityNotFoundException("Item with id " + id
                    + " not in your shopping cart");
        }
    }
}
