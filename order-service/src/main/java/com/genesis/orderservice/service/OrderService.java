package com.genesis.orderservice.service;

import com.genesis.commons.response.ListResponse;
import com.genesis.commons.response.RestResponse;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;

public interface OrderService {

    RestResponse<ListResponse<OrderResponse>> getListOrder(int page, int size, Long userId, boolean all, boolean failure);

    RestResponse<OrderResponse> createOrder(CreateOrderRequest request);

}
