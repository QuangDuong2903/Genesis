package com.genesis.orderservice.saga.createorder;

public enum CreateOrderSagaEvent {
    REDUCE_QUANTITY,
    CANCEL_INVENTORY,
    DEBIT_BALANCE,
    CANCEL_PAYMENT,
    COMPENSATE_INVENTORY,
    CANCEL_ORDER,
    COMPLETE_ORDER
}
