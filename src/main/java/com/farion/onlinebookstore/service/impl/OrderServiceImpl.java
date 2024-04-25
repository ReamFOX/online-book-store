package com.farion.onlinebookstore.service.impl;

import static com.farion.onlinebookstore.entity.Order.Status;

import com.farion.onlinebookstore.dto.item.order.OrderItemDto;
import com.farion.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.farion.onlinebookstore.dto.order.OrderDto;
import com.farion.onlinebookstore.dto.order.UpdateOrderStatusDto;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.OrderItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.exception.EmptyCartException;
import com.farion.onlinebookstore.exception.InvalidStatusException;
import com.farion.onlinebookstore.mapper.OrderMapper;
import com.farion.onlinebookstore.repository.OrderRepository;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.service.OrderItemService;
import com.farion.onlinebookstore.service.OrderService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
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

    @Override
    public OrderItemDto getItemFromOrder(Long orderId, Long itemId, Long userId) {
        Order order = getOrderByUser(orderId, userId);
        return itemService.getOrderItem(order, itemId);
    }

    @Override
    public Set<OrderItemDto> getAllItemsFromOrder(Long orderId, Long userId) {
        Order order = getOrderByUser(orderId, userId);
        return itemService.getOrderItems(order);
    }

    @Override
    public OrderDto updateOrderStatus(Long id, UpdateOrderStatusDto statusDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find order with id " + id)
        );
        Status status = getStatusIfValid(statusDto.getStatus());
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderDto> getAllByUser(Long id) {
        Set<Order> orders = orderRepository.findByUser_Id(id);
        if (orders.isEmpty()) {
            throw new EntityNotFoundException("Your order history is empty");
        }
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    private Order saveOrder(CreateOrderRequestDto requestDto, Long id) {
        ShoppingCart cart = cartRepository.findByUser_Id(id).orElseThrow(() ->
                new EntityNotFoundException("Can`t find shopping cart by "
                        + "user id: " + id));
        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Your cart is empty");
        }
        Order order = new Order();
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

    private Order getOrderByUser(Long orderId, Long userId) {
        return orderRepository.findByIdAndUser_Id(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "You don`t have order with id " + orderId));
    }

    private Status getStatusIfValid(String requestStatus) {
        return Arrays.stream(Status.values())
                .filter(status -> status.name().equals(requestStatus))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidStatusException("Status " + requestStatus
                                + " doesn't exist")
                );
    }
}
