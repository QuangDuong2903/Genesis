package com.genesis.orderservice.saga.createorder;

import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class CreateOrderSagaManager {

    private final StateMachineFactory<CreateOrderSagaState, CreateOrderSagaEvent> stateMachineFactory;

    private final Map<Long, StateMachine<CreateOrderSagaState, CreateOrderSagaEvent>> stateMachines =
            new ConcurrentHashMap<>();

    public void putSaga(CreateOrderAggregate aggregate) {
        StateMachine<CreateOrderSagaState, CreateOrderSagaEvent> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("aggregate", aggregate);
        stateMachines.put(aggregate.getOrderId(), stateMachine);
        stateMachine.startReactively().subscribe();
    }

    public void sendEvent(Long id, CreateOrderSagaEvent event) {
        stateMachines.get(id).sendEvent(Mono.just(MessageBuilder.withPayload(event).build()))
                .subscribe();
    }

}
