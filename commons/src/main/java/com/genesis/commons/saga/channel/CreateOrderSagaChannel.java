package com.genesis.commons.saga.channel;

public interface CreateOrderSagaChannel {
    String REDUCE_QUANTITY = "reduce-quantity-topic";

    String COMPENSATE_QUANTITY = "compensate-quantity-topic";

    String DEBIT_BALANCE = "debit-balance-topic";

}
