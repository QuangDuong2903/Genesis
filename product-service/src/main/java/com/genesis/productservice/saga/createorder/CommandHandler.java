package com.genesis.productservice.saga.createorder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import com.genesis.commons.messaging.Command;
import com.genesis.commons.messaging.Reply;
import com.genesis.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class CommandHandler {

    private final ObjectMapper objectMapper;

    private final ProductService productService;

    @Bean
    public Function<Message<Command<Long, CreateOrderAggregate>>, Message<Reply<Long, CreateOrderAggregate>>>
    handleReduceQuantityCommand() {
        return message -> {
            Command<Long, CreateOrderAggregate> command = message.getPayload();
            CreateOrderAggregate aggregate = objectMapper.convertValue(command.payload(),
                    new TypeReference<>() {});
            try {
                aggregate.getItems()
                        .forEach(item -> productService.reduceQuantity(item.getId(), item.getQuantity()));
            } catch (Exception e) {
                return MessageBuilder.withPayload(Reply.failure(command.identifier(), aggregate)).build();
            }
            return MessageBuilder.withPayload(Reply.success(command.identifier(), aggregate)).build();
        };
    }

    @Bean
    public Function<Message<Command<Long, CreateOrderAggregate>>, Message<Reply<Long, CreateOrderAggregate>>>
    handleCompensateQuantityCommand() {
        return message -> {
            Command<Long, CreateOrderAggregate> command = message.getPayload();
            CreateOrderAggregate aggregate = objectMapper.convertValue(command.payload(),
                    new TypeReference<>() {});
            try {
                aggregate.getItems()
                        .forEach(item -> productService.compensateQuantity(item.getId(), item.getQuantity()));
            } catch (Exception e) {
                return MessageBuilder.withPayload(Reply.failure(command.identifier(), aggregate)).build();
            }
            return MessageBuilder.withPayload(Reply.success(command.identifier(), aggregate)).build();
        };
    }

}
