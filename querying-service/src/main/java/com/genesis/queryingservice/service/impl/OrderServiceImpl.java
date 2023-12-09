package com.genesis.queryingservice.service.impl;

import com.genesis.commons.cqrs.aggregate.OrderAggregate;
import com.genesis.commons.enumeration.OrderStatus;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.queryingservice.entity.Order;
import com.genesis.queryingservice.entity.OrderProduct;
import com.genesis.queryingservice.entity.OrderProductKey;
import com.genesis.queryingservice.entity.Product;
import com.genesis.queryingservice.entity.User;
import com.genesis.queryingservice.mapper.OrderMapper;
import com.genesis.queryingservice.repository.OrderRepository;
import com.genesis.queryingservice.repository.ProductRepository;
import com.genesis.queryingservice.repository.UserRepository;
import com.genesis.queryingservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @Override
    public void createOrder(OrderAggregate aggregate) {
        Order order = orderMapper.toOrder(aggregate);
        setOrderProductsForOrder(aggregate, order);
    }

    @Override
    public void updateOrder(OrderAggregate aggregate) {
        Order order = orderRepository.findById(aggregate.id())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", aggregate.id()));
        orderMapper.toOrder(order, aggregate);
        setOrderProductsForOrder(aggregate, order);
    }

    private void setOrderProductsForOrder(OrderAggregate aggregate, Order order) {
        order.setOrderProducts(aggregate.items().stream().map(item ->
                OrderProduct.builder()
                        .id(OrderProductKey.builder()
                                .orderId(aggregate.id())
                                .productId(item.productId())
                                .build())
                        .product(productRepository.findById(item.productId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", item.productId()))
                        )
                        .order(order)
                        .quantity(item.quantity())
                        .build()
        ).collect(Collectors.toSet()));
        orderRepository.save(order);
    }
}
