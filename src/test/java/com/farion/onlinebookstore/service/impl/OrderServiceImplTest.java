package com.farion.onlinebookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import com.farion.onlinebookstore.dto.order.CreateOrderRequestDto;
import com.farion.onlinebookstore.dto.order.OrderDto;
import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.OrderItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.mapper.OrderMapper;
import com.farion.onlinebookstore.repository.OrderRepository;
import com.farion.onlinebookstore.repository.ShoppingCartRepository;
import com.farion.onlinebookstore.service.OrderItemService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ShoppingCartRepository cartRepository;
    @Mock
    private ShoppingCartService shoppingCartService;
    @Mock
    private OrderItemService itemService;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Place a new order")
    void placeOrder_ValidRequest_ReturnsOrderDto() {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartItems(Set.of(new CartItem()));

        Order order = new Order();
        order.setOrderItems(Set.of(new OrderItem()));
        OrderDto expected = new OrderDto();

        CreateOrderRequestDto requestDto = new CreateOrderRequestDto();

        when(cartRepository.findByUser_Id(anyLong())).thenReturn(Optional.of(cart));
        when(itemService.createOrderItems(anySet())).thenReturn(new HashSet<>());
        when(orderRepository.save(any())).thenReturn(order);
        when(orderMapper.toDto(any())).thenReturn(expected);

        OrderDto actual = orderService.placeOrder(requestDto, 1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Can`t have order by id for specific user")
    void getItemsFromOrder_InvalidRequest_ThrowsException() {
        Long expectedId = 1L;
        String expectedMessage = "You don`t have order with id " + expectedId;

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderService.getAllItemsFromOrder(expectedId, expectedId));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
