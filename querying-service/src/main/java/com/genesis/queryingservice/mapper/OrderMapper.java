package com.genesis.queryingservice.mapper;

import com.genesis.commons.cqrs.aggregate.OrderAggregate;
import com.genesis.queryingservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = UserMapper.class)
public interface OrderMapper {

    @Mapping(source = "userId", target = "user")
    Order toOrder(OrderAggregate aggregate);

    @Mapping(source = "userId", target = "user")
    void toOrder(@MappingTarget Order order, OrderAggregate aggregate);

}
