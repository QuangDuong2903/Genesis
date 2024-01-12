package com.genesis.queryingservice.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.commons.cqrs.aggregate.CategoryAggregate;
import com.genesis.commons.cqrs.aggregate.OrderAggregate;
import com.genesis.commons.cqrs.aggregate.ProductAggregate;
import com.genesis.commons.cqrs.aggregate.UserAggregate;
import com.genesis.commons.messaging.Command;
import com.genesis.queryingservice.service.CategoryService;
import com.genesis.queryingservice.service.OrderService;
import com.genesis.queryingservice.service.ProductService;
import com.genesis.queryingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class CommandHandler {

    private final ObjectMapper objectMapper;

    private final CategoryService categoryService;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @Bean
    public Consumer<Message<Command<Long, CategoryAggregate>>> handleCreateCategoryCommand() {
        return message -> {
            CategoryAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            categoryService.createCategory(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, CategoryAggregate>>> handleUpdateCategoryCommand() {
        return message -> {
            CategoryAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            categoryService.updateCategory(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, OrderAggregate>>> handleCreateOrderCommand() {
        return message -> {
            OrderAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            orderService.createOrder(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, OrderAggregate>>> handleUpdateOrderCommand() {
        return message -> {
            OrderAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            orderService.updateOrder(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, ProductAggregate>>> handleCreateProductCommand() {
        return message -> {
            ProductAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            productService.createProduct(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, ProductAggregate>>> handleUpdateProductCommand() {
        return message -> {
            ProductAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            productService.updateProduct(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, UserAggregate>>> handleCreateUserCommand() {
        return message -> {
            UserAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            userService.createUser(aggregate);
        };
    }

    @Bean
    public Consumer<Message<Command<Long, UserAggregate>>> handleUpdateUserCommand() {
        return message -> {
            UserAggregate aggregate = objectMapper.convertValue(message.getPayload().payload(),
                    new TypeReference<>() {});
            userService.updateUser(aggregate);
        };
    }

}
