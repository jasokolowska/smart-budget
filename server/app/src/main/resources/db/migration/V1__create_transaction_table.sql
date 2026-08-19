-- Flyway Migration Script
-- File: V1__create_transactions_table.sql
-- Description: Create table for storing user financial transactions

CREATE TABLE transactions (
                              id BIGSERIAL PRIMARY KEY,
                              user_id VARCHAR(100) NOT NULL,
                              date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              amount NUMERIC(12, 2) NOT NULL,
                              currency VARCHAR(10) DEFAULT 'PLN' NOT NULL,
                              description TEXT NOT NULL,
                              category VARCHAR(100),
                              subcategory VARCHAR(100),
                              source VARCHAR(50) NOT NULL,          -- e.g. CSV, WEBHOOK, MANUAL
                              recurring BOOLEAN DEFAULT FALSE,
                              atm_withdrawal BOOLEAN DEFAULT FALSE,
                              created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
                              updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL
);

-- Useful indexes for queries and filtering
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date ON transactions(date);
CREATE INDEX idx_transactions_category ON transactions(category);
CREATE INDEX idx_transactions_source ON transactions(source);

-- For future expansion: enforce positive or negative amount rules via CHECK
ALTER TABLE transactions
    ADD CONSTRAINT chk_amount_nonzero CHECK (amount <> 0);
