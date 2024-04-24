package com.farion.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusDto {
    @NotBlank
    private String status;
}
