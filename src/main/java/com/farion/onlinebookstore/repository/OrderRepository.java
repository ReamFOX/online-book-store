package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}