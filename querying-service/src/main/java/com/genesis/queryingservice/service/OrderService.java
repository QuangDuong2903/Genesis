package com.genesis.queryingservice.service;

import com.genesis.commons.cqrs.aggregate.OrderAggregate;

public interface OrderService {

    void createOrder(OrderAggregate aggregate);

    void updateOrder(OrderAggregate aggregate);

}
