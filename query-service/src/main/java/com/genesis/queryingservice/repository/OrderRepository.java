package com.genesis.queryingservice.repository;

import com.genesis.queryingservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("""
                    SELECT DISTINCT u
                    FROM Order u
                    JOIN FETCH u.orderProducts o
                    WHERE o.product.price > :price
            """)
    Page<Order> findAllOrderHaveProductPriceGreaterThan(BigDecimal price, Pageable pageable);

}
