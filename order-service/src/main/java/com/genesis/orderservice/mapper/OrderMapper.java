package com.genesis.orderservice.mapper;

import com.genesis.commons.enumeration.OrderStatus;
import com.genesis.commons.utils.SecurityUtils;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;
import com.genesis.orderservice.entity.Order;
import com.genesis.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    default Order toOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .userId(SecurityUtils.getUserId())
                .total(request.total())
                .status(OrderStatus.CREATED)
                .items(request.items().stream().map(item -> OrderItem.builder()
                        .productId(Long.valueOf(item.productId()))
                        .quantity(item.quantity())
                        .build()).toList())
                .build();
        order.getItems().forEach(item -> item.setOrder(order));
        return order;
    }

    OrderResponse toOrderResponse(Order order);

}
