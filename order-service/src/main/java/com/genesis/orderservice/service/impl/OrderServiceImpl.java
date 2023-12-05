package com.genesis.orderservice.service.impl;

import com.genesis.commons.response.RestResponse;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;
import com.genesis.orderservice.entity.Order;
import com.genesis.orderservice.mapper.OrderMapper;
import com.genesis.orderservice.repository.OrderRepository;
import com.genesis.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public RestResponse<OrderResponse> createOrder(CreateOrderRequest request) {
        Order order = orderMapper.toOrder(request);
        orderRepository.save(order);
        return RestResponse.created(orderMapper.toOrderResponse(order));
    }
}
