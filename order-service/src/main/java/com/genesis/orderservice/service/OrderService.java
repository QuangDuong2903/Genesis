package com.genesis.orderservice.service;

import com.genesis.commons.response.RestResponse;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;

public interface OrderService {

    RestResponse<OrderResponse> createOrder(CreateOrderRequest request);

}
