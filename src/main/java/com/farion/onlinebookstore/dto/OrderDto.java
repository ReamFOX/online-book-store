package com.farion.onlinebookstore.dto;

import static com.farion.onlinebookstore.entity.Order.Status;

import com.farion.onlinebookstore.dto.item.order.OrderItemDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;
}
