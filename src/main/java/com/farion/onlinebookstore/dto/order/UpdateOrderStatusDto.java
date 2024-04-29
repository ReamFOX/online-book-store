package com.farion.onlinebookstore.dto.order;

import static com.farion.onlinebookstore.entity.Order.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusDto {
    @NotBlank
    private String status;
}