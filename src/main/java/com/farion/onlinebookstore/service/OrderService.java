package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.item.order.OrderItemDto;
import com.farion.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.farion.onlinebookstore.dto.order.OrderDto;
import com.farion.onlinebookstore.dto.order.UpdateOrderStatusDto;
import java.util.Set;

public interface OrderService {
    OrderDto placeOrder(CreateOrderRequestDto requestDto, Long id);

    OrderItemDto getItemFromOrder(Long orderId, Long itemId, Long userId);

    Set<OrderItemDto> getAllItemsFromOrder(Long orderId, Long userId);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusDto statusDto);

    Set<OrderDto> getAllByUser(Long id);
}
