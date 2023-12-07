CREATE TABLE "user"
(
    id         BIGINT                      NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    username   VARCHAR(255)                NOT NULL,
    password   VARCHAR(255)                NOT NULL,
    balance    DECIMAL                     NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE role
(
    id         BIGINT                      NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    name       VARCHAR(255)                NOT NULL,
    code       VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES "user" (id);

CREATE TABLE category
(
    id         BIGINT                      NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    name       VARCHAR(255)                NOT NULL,
    slug       VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          BIGINT                      NOT NULL,
    version     SMALLINT                    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    name        VARCHAR(255)                NOT NULL,
    description VARCHAR(255)                NOT NULL,
    image       VARCHAR(255)                NOT NULL,
    quantity    BIGINT                      NOT NULL,
    price       DECIMAL                     NOT NULL,
    category_id BIGINT                      NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

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

CREATE TABLE order_product
(
    quantity   BIGINT NOT NULL,
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT pk_order_product PRIMARY KEY (order_id, product_id)
);

ALTER TABLE order_product
    ADD CONSTRAINT FK_ORDER_PRODUCT_ON_ORDER FOREIGN KEY (order_id) REFERENCES "'order'" (id);

ALTER TABLE order_product
    ADD CONSTRAINT FK_ORDER_PRODUCT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

