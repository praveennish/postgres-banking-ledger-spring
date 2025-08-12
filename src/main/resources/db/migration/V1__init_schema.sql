CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
)

CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(customer_id)
    account_type VARCHAR(10) NOT NULL,
    balance NUMERIC(15, 2) DEFAULT 0.00
)

CREATE TABLE transactions (
    txn_id BIGSERIAL PRIMARY KEY,
    account_id INT REFERENCES accounts(account_id),
    amount NUMERIC(15, 2) NOT NULL,
    txn_type VARCHAR(20) CHECK( txn_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),
    txn_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)