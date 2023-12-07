package com.genesis.queryingservice.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.commons.cqrs.aggregate.CategoryAggregate;
import com.genesis.commons.messaging.Command;
import com.genesis.queryingservice.service.CategoryService;
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

}
