package com.farion.onlinebookstore.controller;

import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.service.CartItemService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {
    private final ShoppingCartService cartService;
    private final CartItemService itemService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get user shopping cart",
            description = "To get the current user's shopping cart")
    public ShoppingCart getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return cartService.getByUserEmail(authentication.getName());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Add book to the cart",
            description = "Adding book to the shopping cart")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartItem addBookToCart(@RequestBody CartItem cartItem) {
        return itemService.save(cartItem);
    }

    @PutMapping("/cart-items/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Update quantity of a book",
            description = "Update quantity of a book in the shopping cart")
    public CartItem updateQuantity(@PathVariable Long id, @RequestBody CartItem cartItem) {
        return null;
    }

    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Remove book from cart",
            description = "Remove a book from the shopping cart")
    public void deleteBookFromCart(@PathVariable Long id) {

    }
}
