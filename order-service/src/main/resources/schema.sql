CREATE TABLE "'order'"
(
    id         BIGINT                      NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    user_id    BIGINT                      NOT NULL,
    total      DECIMAL                     NOT NULL,
    status     VARCHAR(255)                NOT NULL,
    CONSTRAINT "pk_'order'" PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id         BIGINT                      NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    product_id BIGINT                      NOT NULL,
    quantity   BIGINT                      NOT NULL,
    order_id   BIGINT                      NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id)
);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES "'order'" (id);
