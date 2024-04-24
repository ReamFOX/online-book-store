package com.farion.onlinebookstore.controller;

import com.farion.onlinebookstore.dto.item.order.OrderItemDto;
import com.farion.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.farion.onlinebookstore.dto.order.OrderDto;
import com.farion.onlinebookstore.dto.order.UpdateOrderStatusDto;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get user`s order history",
            description = "Retrieve user's order history")
    public Set<OrderDto> getOrderHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllByUser(user.getId());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Place an order",
            description = "Enables users to submit requests for purchasing books")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDto placeOrder(@RequestBody @Valid CreateOrderRequestDto requestDto,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(requestDto, user.getId());
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get order items from order",
            description = "Retrieve all order items for a specific order")
    public Set<OrderItemDto> getAllItemsFromOrder(@PathVariable Long orderId,
                                                  Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllItemsFromOrder(orderId, user.getId());
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get specific order item from order",
            description = "Retrieve a specific OrderItem within an order")
    public OrderItemDto getItemFromOrder(@PathVariable Long orderId,
                                         @PathVariable Long itemId,
                                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getItemFromOrder(orderId, itemId, user.getId());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update order status",
            description = "Change the status of the order by id")
    public OrderDto updateOrderStatus(@PathVariable Long id,
                                  @RequestBody UpdateOrderStatusDto statusDto) {
        return orderService.updateOrderStatus(id, statusDto);
    }
}
