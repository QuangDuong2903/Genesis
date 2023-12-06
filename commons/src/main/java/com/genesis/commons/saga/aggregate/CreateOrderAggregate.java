package com.genesis.commons.saga.aggregate;

import com.genesis.commons.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateOrderAggregate {
    private Long orderId;
    private Long userId;
    private BigDecimal total;
    private OrderStatus status;
    private List<OrderItem> items;

    @Data
    @AllArgsConstructor
    public static class OrderItem {
        private Long id;
        private Long quantity;
    }

}
