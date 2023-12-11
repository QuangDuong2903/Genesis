package com.genesis.queryingservice.mapper;

import com.genesis.commons.cqrs.aggregate.OrderAggregate;
import com.genesis.queryingservice.dto.response.OrderResponse;
import com.genesis.queryingservice.entity.Order;
import com.genesis.queryingservice.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(uses = UserMapper.class)
public interface OrderMapper {

    @Mapping(source = "userId", target = "user")
    Order toOrder(OrderAggregate aggregate);

    @Mapping(source = "userId", target = "user")
    void toOrder(@MappingTarget Order order, OrderAggregate aggregate);

    OrderResponse toOrderResponse(Order order);

    default List<OrderResponse.OrderProductResponse> setOrderProductToListOrderProductResponse(
            Set<OrderProduct> orderProducts) {
        return orderProducts.stream().map(orderProduct -> OrderResponse.OrderProductResponse.builder()
                .id(orderProduct.getProduct().getId().toString())
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getProduct().getPrice())
                .quantity(orderProduct.getQuantity())
                .build()
        ).toList();
    }

}
