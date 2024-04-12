package com.farion.onlinebookstore.dto.category;

import java.math.BigDecimal;

public record CategoryDto(
    Long id,
    String name,
    String description
) {
}

