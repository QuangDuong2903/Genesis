package com.genesis.orderservice.service.impl;

import com.genesis.commons.enumeration.OrderStatus;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.commons.persistence.BaseEntity;
import com.genesis.commons.response.RestResponse;
import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;
import com.genesis.orderservice.entity.Order;
import com.genesis.orderservice.mapper.OrderMapper;
import com.genesis.orderservice.repository.OrderRepository;
import com.genesis.orderservice.saga.createorder.CreateOrderSagaManager;
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

    private final CreateOrderSagaManager createOrderSagaManager;

    @Override
    public RestResponse<OrderResponse> createOrder(CreateOrderRequest request) {
        Order order = orderMapper.toOrder(request);
        orderRepository.save(order);
        CreateOrderAggregate aggregate = CreateOrderAggregate.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .total(order.getTotal())
                .status(order.getStatus())
                .items(order.getItems().stream().map(item ->
                        new CreateOrderAggregate.OrderItem(item.getProductId(), item.getQuantity())
                ).toList())
                .build();
        createOrderSagaManager.putSaga(aggregate);
        return RestResponse.created(orderMapper.toOrderResponse(order));
    }

}
