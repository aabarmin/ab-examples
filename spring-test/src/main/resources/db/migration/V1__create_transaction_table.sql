create table TRANSACTIONS
(
    ID   SERIAL          not null,
    ACCOUNT_FROM int not null,
    ACCOUNT_TO int not null,
    AMOUNT_CURRENCY VARCHAR(32) not null,
    AMOUNT_VALUE decimal(10,2) not null,
    TRANSACTION_STATUS VARCHAR(32) not null,
    PRIMARY KEY (ID)
);