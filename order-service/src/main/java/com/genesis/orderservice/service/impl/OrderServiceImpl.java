package com.genesis.orderservice.service.impl;

import com.genesis.commons.cqrs.aggregate.OrderAggregate;
import com.genesis.commons.cqrs.channel.CQRSChannel;
import com.genesis.commons.enumeration.OrderStatus;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.commons.messaging.Command;
import com.genesis.commons.persistence.BaseEntity;
import com.genesis.commons.response.ListResponse;
import com.genesis.commons.response.RestResponse;
import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;
import com.genesis.orderservice.entity.Order;
import com.genesis.orderservice.mapper.OrderMapper;
import com.genesis.orderservice.repository.OrderRepository;
import com.genesis.orderservice.saga.createorder.CreateOrderSagaManager;
import com.genesis.orderservice.service.OrderService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final CreateOrderSagaManager createOrderSagaManager;

    private final StreamBridge streamBridge;

    @Override
    public RestResponse<ListResponse<OrderResponse>> getListOrder(int page, int size, Long userId,
                                                                  boolean all, boolean failure) {
        if (failure)
            throw new RuntimeException("Exception at order service");
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<OrderResponse> responses = orderRepository
                .findAllByUserIdWithOrderItems(userId, pageable)
                .map(orderMapper::toOrderResponse);
        return RestResponse.ok(ListResponse.of(responses));
    }

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
        streamBridge.send(CQRSChannel.CREATE_ORDER, MessageBuilder.withPayload(
                new Command<>(order.getId(), orderMapper.toAggregate(order))).build());
        return RestResponse.created(orderMapper.toOrderResponse(order));
    }

}
