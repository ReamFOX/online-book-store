package com.farion.onlinebookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.farion.onlinebookstore.dto.item.orderitem.OrderItemDto;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.OrderItem;
import com.farion.onlinebookstore.mapper.OrderItemMapper;
import com.farion.onlinebookstore.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemMapper orderItemMapper;
    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    @DisplayName("Get order item from order")
    void getOrderItem_ExistingItemId_ReturnsOrderItemDto() {
        Long itemId = 1L;
        Order order = new Order();
        order.setId(1L);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(itemId);
        orderItem.setOrder(order);
        OrderItemDto expected = new OrderItemDto();
        expected.setId(itemId);

        order.setOrderItems(Set.of(orderItem));
        when(orderItemMapper.toDto(orderItem)).thenReturn(expected);

        OrderItemDto result = orderItemService.getOrderItem(order, itemId);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Don`t have specific item in order and throw exception")
    void getOrderItem_NonExistingItemId_ThrowsEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = "Can`t find item with id " + expectedId
                + " in your order with id " + expectedId;
        Order order = new Order();
        order.setId(expectedId);
        order.setOrderItems(Collections.emptySet());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderItemService.getOrderItem(order, expectedId));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
