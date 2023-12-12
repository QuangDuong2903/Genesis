package com.genesis.orderservice.controller;

import com.genesis.commons.response.ListResponse;
import com.genesis.commons.response.RestResponse;
import com.genesis.orderservice.dto.request.CreateOrderRequest;
import com.genesis.orderservice.dto.response.OrderResponse;
import com.genesis.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponse<ListResponse<OrderResponse>>> getListOrder(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam Long userId,
            @RequestParam(required = false) boolean all,
            @RequestParam(required = false) boolean failure
    ) {
        return ResponseEntity.ok(orderService.getListOrder(page, size, userId, all, failure));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestResponse<OrderResponse>> createOrder(@RequestBody @Valid
                                                                   CreateOrderRequest request) {
        RestResponse<OrderResponse> response = orderService.createOrder(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

}
