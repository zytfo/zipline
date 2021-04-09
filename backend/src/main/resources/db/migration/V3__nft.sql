CREATE TABLE wallets
(
    id      BIGSERIAL
        CONSTRAINT wallets_pk
            PRIMARY KEY,
    name    VARCHAR(100)  NOT NULL
        UNIQUE,
    address VARCHAR(50)   NOT NULL
        UNIQUE,
    sk      VARCHAR(100)  NOT NULL
        UNIQUE,
    ss      VARCHAR(100)  NOT NULL
        UNIQUE,
    sv      VARCHAR(1000) NOT NULL
        UNIQUE
);

CREATE TABLE user_wallets
(
    user_id   BIGINT NOT NULL,
    wallet_id BIGINT NOT NULL,
    CONSTRAINT user_wallets_pk
        PRIMARY KEY (user_id, wallet_id)
);

CREATE TABLE nfts
(
    id            BIGSERIAL
        CONSTRAINT nfts_pk
            PRIMARY KEY,
    name          VARCHAR(100)   NOT NULL
        UNIQUE,
    description   VARCHAR(50000) NOT NULL,
    external_link VARCHAR(2048),
    image_url     VARCHAR(2048)  NOT NULL
        UNIQUE
);

CREATE TABLE wallet_nfts
(
    wallet_id BIGINT NOT NULL,
    nft_id    BIGINT NOT NULL,
    CONSTRAINT wallet_nfts_pk
        PRIMARY KEY (wallet_id, nft_id)
);
