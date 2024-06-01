CREATE TABLE auth_requests (
    id SERIAL NOT NULL,
    from_id INT NOT NULL,
    to_id INT NOT NULL,
    amount_currency VARCHAR(255) NOT NULL,
    amount_value INT NOT NULL
);
