create table STEPS
(
    ID   SERIAL          not null,
    TRANSACTION_ID int not null,
    AMOUNT_CURRENCY VARCHAR(32) not null,
    AMOUNT_VALUE decimal(10,2) not null,
    CREATED_AT TIMESTAMP not null,
    PRIMARY KEY (ID),
    FOREIGN KEY (TRANSACTION_ID) REFERENCES TRANSACTIONS(ID)
);

