package com.genesis.orderservice.saga.createorder;

import com.genesis.commons.enumeration.OrderStatus;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.commons.saga.aggregate.CreateOrderAggregate;
import com.genesis.commons.saga.channel.CreateOrderSagaChannel;
import com.genesis.commons.saga.command.Command;
import com.genesis.orderservice.entity.Order;
import com.genesis.orderservice.repository.OrderRepository;
import com.genesis.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import reactor.core.publisher.Mono;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class CreateOrderSagaStateMachineConfiguration extends
        StateMachineConfigurerAdapter<CreateOrderSagaState, CreateOrderSagaEvent> {

    private final StreamBridge streamBridge;

    private final OrderRepository orderRepository;

    @Override
    public void configure(StateMachineConfigurationConfigurer<CreateOrderSagaState, CreateOrderSagaEvent> config) throws Exception {
        config.withConfiguration()
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<CreateOrderSagaState, CreateOrderSagaEvent> states) throws Exception {
        states.withStates()
                .initial(CreateOrderSagaState.CREATED)
                .state(CreateOrderSagaState.CREATED, reduceQuantity())
                .state(CreateOrderSagaState.INVENTORY_REDUCED, debitBalance())
                .state(CreateOrderSagaState.INVENTORY_ERROR, cancelOrder())
                .state(CreateOrderSagaState.INVENTORY_COMPENSATED, cancelOrder())
                .state(CreateOrderSagaState.PAYMENT_PROCESSED, completeOrder())
                .state(CreateOrderSagaState.PAYMENT_ERROR, compensateQuantity())
                .end(CreateOrderSagaState.CANCELED)
                .end(CreateOrderSagaState.COMPLETED)
                .states(EnumSet.allOf(CreateOrderSagaState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<CreateOrderSagaState, CreateOrderSagaEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(CreateOrderSagaState.CREATED)
                .target(CreateOrderSagaState.INVENTORY_REDUCED)
                .event(CreateOrderSagaEvent.REDUCE_QUANTITY)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.CREATED)
                .target(CreateOrderSagaState.INVENTORY_ERROR)
                .event(CreateOrderSagaEvent.CANCEL_INVENTORY)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.INVENTORY_ERROR)
                .target(CreateOrderSagaState.CANCELED)
                .event(CreateOrderSagaEvent.CANCEL_ORDER)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.INVENTORY_REDUCED)
                .target(CreateOrderSagaState.PAYMENT_PROCESSED)
                .event(CreateOrderSagaEvent.DEBIT_BALANCE)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.INVENTORY_REDUCED)
                .target(CreateOrderSagaState.PAYMENT_ERROR)
                .event(CreateOrderSagaEvent.CANCEL_PAYMENT)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.PAYMENT_PROCESSED)
                .target(CreateOrderSagaState.COMPLETED)
                .event(CreateOrderSagaEvent.COMPLETE_ORDER)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.PAYMENT_ERROR)
                .target(CreateOrderSagaState.INVENTORY_COMPENSATED)
                .event(CreateOrderSagaEvent.COMPENSATE_INVENTORY)
                .and()
                .withExternal()
                .source(CreateOrderSagaState.INVENTORY_COMPENSATED)
                .target(CreateOrderSagaState.CANCELED)
                .event(CreateOrderSagaEvent.CANCEL_ORDER);
    }

    @Bean
    public StateMachineListener<CreateOrderSagaState, CreateOrderSagaEvent> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<CreateOrderSagaState, CreateOrderSagaEvent> from, State<CreateOrderSagaState, CreateOrderSagaEvent> to) {
                log.info("Create order saga state machine change to " + to.getId());
            }
        };
    }

    @Bean
    public Action<CreateOrderSagaState, CreateOrderSagaEvent> reduceQuantity() {
        return ctx -> {
            CreateOrderAggregate aggregate = ctx.getExtendedState().get("aggregate", CreateOrderAggregate.class);
            streamBridge.send(CreateOrderSagaChannel.REDUCE_QUANTITY,
                    MessageBuilder.withPayload(new Command<>(aggregate.getOrderId(), aggregate)).build()
            );
        };
    }

    @Bean
    public Action<CreateOrderSagaState, CreateOrderSagaEvent> compensateQuantity() {
        return ctx -> {
            CreateOrderAggregate aggregate = ctx.getExtendedState().get("aggregate", CreateOrderAggregate.class);
            streamBridge.send(CreateOrderSagaChannel.COMPENSATE_QUANTITY,
                    MessageBuilder.withPayload(new Command<>(aggregate.getOrderId(), aggregate)).build()
            );
        };
    }

    @Bean
    public Action<CreateOrderSagaState, CreateOrderSagaEvent> debitBalance() {
        return ctx -> {
            CreateOrderAggregate aggregate = ctx.getExtendedState().get("aggregate", CreateOrderAggregate.class);
            streamBridge.send(CreateOrderSagaChannel.DEBIT_BALANCE,
                    MessageBuilder.withPayload(new Command<>(aggregate.getOrderId(), aggregate)).build()
            );
        };
    }

    @Bean
    public Action<CreateOrderSagaState, CreateOrderSagaEvent> cancelOrder() {
        return ctx -> {
            CreateOrderAggregate aggregate = ctx.getExtendedState().get("aggregate", CreateOrderAggregate.class);
            cancelOrder(aggregate.getOrderId());
            ctx.getStateMachine().sendEvent(Mono.just(MessageBuilder.withPayload(CreateOrderSagaEvent.CANCEL_ORDER)
                    .build())).subscribe();
        };
    }

    @Bean
    public Action<CreateOrderSagaState, CreateOrderSagaEvent> completeOrder() {
        return ctx -> {
            CreateOrderAggregate aggregate = ctx.getExtendedState().get("aggregate", CreateOrderAggregate.class);
            completeOrder(aggregate.getOrderId());
            ctx.getStateMachine().sendEvent(Mono.just(MessageBuilder.withPayload(CreateOrderSagaEvent.COMPLETE_ORDER)
                    .build())).subscribe();
        };
    }

    public void completeOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

}
