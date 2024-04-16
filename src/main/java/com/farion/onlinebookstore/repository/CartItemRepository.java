package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
