package com.genesis.orderservice.saga.createorder;

import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import com.genesis.commons.messaging.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class ReplyHandler {

    private final CreateOrderSagaManager createOrderSagaManager;

    @Bean
    public Consumer<Message<Reply<Long, CreateOrderAggregate>>> handleReduceQuantityReply() {
        return message -> {
            Reply<Long, CreateOrderAggregate> reply = message.getPayload();
            if (reply.success())
                createOrderSagaManager.sendEvent(reply.identifier(), CreateOrderSagaEvent.REDUCE_QUANTITY);
            else
                createOrderSagaManager.sendEvent(reply.identifier(), CreateOrderSagaEvent.CANCEL_INVENTORY);
        };
    }

    @Bean
    public Consumer<Message<Reply<Long, CreateOrderAggregate>>> handleDebitBalanceReply() {
        return message -> {
            Reply<Long, CreateOrderAggregate> reply = message.getPayload();
            if (reply.success())
                createOrderSagaManager.sendEvent(reply.identifier(), CreateOrderSagaEvent.DEBIT_BALANCE);
            else
                createOrderSagaManager.sendEvent(reply.identifier(), CreateOrderSagaEvent.CANCEL_PAYMENT);
        };
    }

    @Bean
    public Consumer<Message<Reply<Long, CreateOrderAggregate>>> handleCompensateQuantityReply() {
        return message -> {
            Reply<Long, CreateOrderAggregate> reply = message.getPayload();
            if (reply.success())
                createOrderSagaManager.sendEvent(reply.identifier(), CreateOrderSagaEvent.COMPENSATE_INVENTORY);
        };
    }

}
