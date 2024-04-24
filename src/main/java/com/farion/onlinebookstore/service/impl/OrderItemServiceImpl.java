package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.item.order.OrderItemDto;
import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.OrderItem;
import com.farion.onlinebookstore.mapper.OrderItemMapper;
import com.farion.onlinebookstore.repository.OrderItemRepository;
import com.farion.onlinebookstore.service.OrderItemService;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository itemRepository;
    private final OrderItemMapper itemMapper;

    @Override
    public Set<OrderItem> createOrderItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::createOrderItem)
                .collect(Collectors.toSet());
    }

    @Override
    public void setOrder(OrderItem orderItem, Order order) {
        orderItem.setOrder(order);
    }

    private OrderItem createOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice().multiply(
                BigDecimal.valueOf(orderItem.getQuantity())));
        return itemRepository.save(orderItem);
    }
}
