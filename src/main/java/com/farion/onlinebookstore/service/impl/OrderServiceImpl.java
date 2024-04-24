package com.farion.onlinebookstore.service.impl;

import static com.farion.onlinebookstore.entity.Order.Status;

import com.farion.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.farion.onlinebookstore.dto.order.OrderDto;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.OrderItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.mapper.OrderMapper;
import com.farion.onlinebookstore.repository.OrderRepository;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.service.OrderItemService;
import com.farion.onlinebookstore.service.OrderService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Status INIT_STATUS = Status.PENDING;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartService cartService;
    private final OrderItemService itemService;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderDto placeOrder(CreateOrderRequestDto requestDto, Long id) {
        Order order = saveOrder(requestDto, id);
        cartService.clearShoppingCartByUserId(id);
        order.getOrderItems()
                .forEach(orderItem -> itemService.setOrder(orderItem, order));
        return orderMapper.toDto(order);
    }

    private Order saveOrder(CreateOrderRequestDto requestDto, Long id) {
        Order order = new Order();
        ShoppingCart cart = cartRepository.getReferenceById(id);
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(INIT_STATUS);
        order.setShippingAddress(requestDto.getShippingAddress());
        Set<OrderItem> orderItems = itemService.createOrderItems(cart.getCartItems());
        order.setOrderItems(orderItems);
        order.setTotal(orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return orderRepository.save(order);
    }
}
