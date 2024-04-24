package com.farion.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderRequestDto {
    @NotBlank
    @Size(max = 255, message = "Shipping address can`t be longer than 255 characters")
    private String shippingAddress;
}
