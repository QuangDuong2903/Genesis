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
