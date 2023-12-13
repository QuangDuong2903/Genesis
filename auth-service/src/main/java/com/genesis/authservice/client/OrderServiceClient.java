package com.genesis.authservice.client;

import com.genesis.authservice.client.dto.response.OrderResponse;
import com.genesis.commons.response.ListResponse;
import com.genesis.commons.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("order-service")
public interface OrderServiceClient {

    String BASE = "/order";

    @GetMapping(value = BASE + "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    RestResponse<ListResponse<OrderResponse>> getListOrder(
            @RequestParam Long userId,
            @RequestParam(required = false) boolean all,
            @RequestParam(required = false) boolean failure,
            @RequestParam(required = false) int delay
    );

}
