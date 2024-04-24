package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.farion.onlinebookstore.dto.order.OrderDto;

public interface OrderService {
    OrderDto placeOrder(CreateOrderRequestDto requestDto, Long id);
}
