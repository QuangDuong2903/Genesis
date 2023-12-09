package com.genesis.queryingservice.controller;

import com.genesis.commons.response.ListResponse;
import com.genesis.commons.response.RestResponse;
import com.genesis.queryingservice.dto.response.OrderResponse;
import com.genesis.queryingservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestResponse<ListResponse<OrderResponse>>> getListPost(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) boolean all,
            @RequestParam BigDecimal price
    ) {
        return ResponseEntity.ok(orderService.getListOrder(page, size, all, price));
    }

}
