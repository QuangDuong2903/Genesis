package com.genesis.orderservice.repository;

import com.genesis.orderservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT u FROM Order u JOIN FETCH u.items WHERE u.id = :id")
    Optional<Order> findByIdWithItems(Long id);

    @Query("SELECT u FROM Order u JOIN FETCH u.items WHERE u.userId = :userId")
    Page<Order> findAllByUserIdWithOrderItems(Long userId, Pageable pageable);
}
