package com.genesis.authservice.saga.createorder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.authservice.service.UserService;
import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import com.genesis.commons.messaging.Command;
import com.genesis.commons.messaging.Reply;
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

    private final UserService userService;

    @Bean
    public Function<Message<Command<Long, CreateOrderAggregate>>, Message<Reply<Long, CreateOrderAggregate>>>
    handleDebitBalanceCommand() {
        return message -> {
            Command<Long, CreateOrderAggregate> command = message.getPayload();
            CreateOrderAggregate aggregate = objectMapper.convertValue(command.payload(),
                    new TypeReference<>() {});
            try {
                userService.debitBalance(aggregate.getUserId(), aggregate.getTotal());
            } catch (Exception e) {
                return MessageBuilder.withPayload(Reply.failure(command.identifier(), aggregate)).build();
            }
            return MessageBuilder.withPayload(Reply.success(command.identifier(), aggregate)).build();
        };
    }

}
