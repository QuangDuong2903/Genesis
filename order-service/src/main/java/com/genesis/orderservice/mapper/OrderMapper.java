package com.genesis.orderservice.mapper;

import com.genesis.commons.utils.SecurityUtils;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;
import com.genesis.orderservice.entity.Order;
import com.genesis.orderservice.entity.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper
public interface OrderMapper {

    @BeanMapping(qualifiedByName = "attachUserIdAndOrderForOrderItems")
    Order toOrder(CreateOrderRequest request);

    OrderResponse toOrderResponse(Order order);

    @AfterMapping
    @Named("attachUserIdAndOrderForOrderItems")
    default void attachUserIdAndOrderForOrderItems(@MappingTarget Order order) {
        order.setUserId(SecurityUtils.getUserId());
        order.getItems().forEach(item -> item.setOrder(order));
    }

}
