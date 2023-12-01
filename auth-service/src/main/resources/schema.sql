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