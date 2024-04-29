package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.Order;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndUser_Id(Long id, Long userId);

    Set<Order> findByUser_Id(Long id);
}