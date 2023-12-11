package com.genesis.queryingservice.service;

import com.genesis.commons.cqrs.aggregate.OrderAggregate;
import com.genesis.commons.response.ListResponse;
import com.genesis.commons.response.RestResponse;
import com.genesis.queryingservice.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    RestResponse<ListResponse<OrderResponse>> getListOrder(int page, int size, boolean all, BigDecimal price);

    void createOrder(OrderAggregate aggregate);

    void updateOrder(OrderAggregate aggregate);

}
