package com.genesis.commons.cqrs.channel;

public interface CQRSChannel {

    String CREATE_USER = "create-user-topic";

    String UPDATE_USER = "update-user-topic";

    String CREATE_PRODUCT = "create-product-topic";

    String UPDATE_PRODUCT = "update-product-topic";

    String CREATE_CATEGORY = "create-category-topic";

    String UPDATE_CATEGORY = "update-category-topic";

    String CREATE_ORDER = "create-order-topic";

    String UPDATE_ORDER = "update-order-topic";

}
