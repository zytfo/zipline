CREATE TABLE users
(
    id                         BIGSERIAL
        CONSTRAINT users_pk
            PRIMARY KEY,
    username                   VARCHAR(20),
    email                      VARCHAR(50),
    password                   VARCHAR(120),
    is_enabled                 BOOLEAN,
    is_account_non_expired     BOOLEAN,
    is_account_non_locked      BOOLEAN,
    is_credentials_non_expired BOOLEAN
);

CREATE UNIQUE INDEX users_email_uindex ON users (email);
CREATE UNIQUE INDEX users_username_uindex ON users (username);

CREATE TABLE roles
(
    id   SERIAL
        CONSTRAINT roles_pk
            PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE confirmation_tokens
(
    id      BIGSERIAL
        CONSTRAINT confirmation_tokens_pk
            PRIMARY KEY,
    token   UUID,
    created TIMESTAMP,
    user_id BIGINT
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id INT    NOT NULL,
    CONSTRAINT user_roles_pk
        PRIMARY KEY (user_id, role_id)
);

CREATE TABLE user_reading_list
(
    user_id BIGINT NOT NULL,
    news_id BIGINT NOT NULL,
    CONSTRAINT user_reading_list_pk
        PRIMARY KEY (user_id, news_id)
);

INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');

CREATE SEQUENCE hibernate_sequence;
ALTER SEQUENCE hibernate_sequence OWNER TO postgres;

CREATE TABLE news
(
    id                BIGSERIAL
        CONSTRAINT news_pk
            PRIMARY KEY,
    created_by        BIGINT,
    title             VARCHAR(10000),
    description       VARCHAR(50000),
    image_url         VARCHAR(2000),
    content           TEXT,
    tags              VARCHAR(5000)[],
    source            VARCHAR(1000),
    created           TIMESTAMP,
    updated           TIMESTAMP
);

CREATE TABLE publications
(
    id         BIGSERIAL
        CONSTRAINT publications_pk
            PRIMARY KEY,
    created_by BIGINT,
    content    TEXT,
    tickers    VARCHAR(5000)[],
    trades     BIGINT[],
    created    TIMESTAMP,
    updated    TIMESTAMP
);

CREATE TABLE likes
(
    id        BIGSERIAL
        CONSTRAINT likes_pk
            PRIMARY KEY,
    user_id   BIGSERIAL NOT NULL,
    post_type BIGINT    NOT NULL,
    post_id   BIGSERIAL NOT NULL,
    date      TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX likes_post_type_post_id_user_id_uindex ON likes (post_type, post_id, user_id);

CREATE TABLE comments
(
    id        BIGSERIAL
        CONSTRAINT comments_pk
            PRIMARY KEY,
    user_id   BIGINT    NOT NULL,
    post_type BIGINT    NOT NULL,
    post_id   BIGSERIAL NOT NULL,
    message   TEXT      NOT NULL,
    created   TIMESTAMP NOT NULL,
    updated   TIMESTAMP
);

CREATE TABLE currencies
(
    id          BIGSERIAL
        CONSTRAINT currencies_pk
            PRIMARY KEY,
    name        VARCHAR(100),
    ticker      VARCHAR(20),
    description TEXT
);

CREATE INDEX idx_currencies_ticker ON currencies (ticker);

CREATE TABLE complaints
(
    id          BIGSERIAL
        CONSTRAINT complaints_pk
            PRIMARY KEY,
    user_id     BIGINT        NOT NULL,
    post_type   BIGINT        NOT NULL,
    post_id     BIGSERIAL     NOT NULL,
    post_author BIGSERIAL     NOT NULL,
    reason      INTEGER       NOT NULL,
    message     VARCHAR(2000) NOT NULL,
    created     TIMESTAMP     NOT NULL
);

CREATE TABLE files
(
    id   UUID CONSTRAINT files_pk PRIMARY KEY,
    name VARCHAR(2000),
    type VARCHAR(1000),
    data BYTEA
);
