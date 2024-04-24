package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.OrderItem;
import java.util.Set;

public interface OrderItemService {
    Set<OrderItem> createOrderItems(Set<CartItem> cartItem);

    void setOrder(OrderItem orderItem, Order order);
}
