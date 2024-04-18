package com.farion.onlinebookstore.repository;

import com.farion.onlinebookstore.entity.CartItem;
import com.farion.onlinebookstore.entity.ShoppingCart;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser_Email(String email);

    @Transactional
    @Modifying
    @Query("update ShoppingCart s set s.cartItems = ?1")
    void updateCartItemsBy(CartItem cartItems);

    Set<ShoppingCart> findByUser_EmailOrderByCartItemsAsc(String email);
}
