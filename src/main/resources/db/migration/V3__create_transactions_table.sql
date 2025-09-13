CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(10) CHECK (type IN ('INCOME', 'EXPENSE')) NOT NULL,
    transaction_date DATE NOT NULL,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);