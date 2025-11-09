-- Create customers table
CREATE TABLE customers (
    customer_id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    middle_name VARCHAR(25),
    last_name VARCHAR(25) NOT NULL,
    ssn VARCHAR(9) NOT NULL UNIQUE,
    date_of_birth DATE NOT NULL,
    fico_score INTEGER,
    address_line1 VARCHAR(50) NOT NULL,
    address_line2 VARCHAR(50),
    city VARCHAR(50) NOT NULL,
    state_code VARCHAR(2) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    country_code VARCHAR(3) NOT NULL,
    phone_number1 VARCHAR(15) NOT NULL,
    phone_number2 VARCHAR(15),
    government_issued_id VARCHAR(20) NOT NULL,
    eft_account_id VARCHAR(10),
    primary_card_holder_indicator VARCHAR(1) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_fico_score CHECK (fico_score IS NULL OR (fico_score >= 300 AND fico_score <= 850)),
    CONSTRAINT chk_primary_holder CHECK (primary_card_holder_indicator IN ('Y', 'N'))
);

-- Create accounts table
CREATE TABLE accounts (
    account_id BIGINT NOT NULL PRIMARY KEY,
    active_status VARCHAR(1) NOT NULL,
    current_balance DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    credit_limit DECIMAL(12, 2) NOT NULL,
    cash_credit_limit DECIMAL(12, 2) NOT NULL,
    open_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    reissue_date DATE,
    current_cycle_credit DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    current_cycle_debit DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    group_id VARCHAR(10),
    customer_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT chk_active_status CHECK (active_status IN ('Y', 'N'))
);

-- Create cards table
CREATE TABLE cards (
    card_number VARCHAR(16) NOT NULL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    card_status VARCHAR(1) NOT NULL,
    expiration_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_card_account FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_card_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Create card_xref table
CREATE TABLE card_xref (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_xref_account FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_xref_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Create index on account_id for card_xref
CREATE INDEX idx_account_id ON card_xref(account_id);

-- Create transactions table
CREATE TABLE transactions (
    transaction_id VARCHAR(16) NOT NULL PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    type_code VARCHAR(2) NOT NULL,
    category_code VARCHAR(4) NOT NULL,
    source VARCHAR(10) NOT NULL,
    description VARCHAR(100),
    amount DECIMAL(12, 2) NOT NULL,
    merchant_id BIGINT,
    merchant_name VARCHAR(50),
    merchant_city VARCHAR(50),
    merchant_zip VARCHAR(10),
    original_timestamp TIMESTAMP NOT NULL,
    processed_timestamp TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create disclosure_groups table
CREATE TABLE disclosure_groups (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_group_id VARCHAR(10) NOT NULL,
    transaction_category_code VARCHAR(4) NOT NULL,
    transaction_type_code VARCHAR(2) NOT NULL,
    interest_rate DECIMAL(5, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_disclosure_group UNIQUE (account_group_id, transaction_category_code, transaction_type_code)
);

-- Create transaction_category_balances table
CREATE TABLE transaction_category_balances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    type_code VARCHAR(2) NOT NULL,
    category_code VARCHAR(4) NOT NULL,
    balance DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tcb_account FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT uk_tcb_account_type_category UNIQUE (account_id, type_code, category_code)
);

-- Create indexes for better query performance
CREATE INDEX idx_customer_ssn ON customers(ssn);
CREATE INDEX idx_customer_last_name ON customers(last_name);
CREATE INDEX idx_account_customer ON accounts(customer_id);
CREATE INDEX idx_account_status ON accounts(active_status);
CREATE INDEX idx_account_group ON accounts(group_id);
CREATE INDEX idx_card_account ON cards(account_id);
CREATE INDEX idx_card_customer ON cards(customer_id);
CREATE INDEX idx_transaction_card ON transactions(card_number);
CREATE INDEX idx_transaction_type_category ON transactions(type_code, category_code);
CREATE INDEX idx_tcb_account ON transaction_category_balances(account_id);

-- Insert default disclosure group for interest calculation
INSERT INTO disclosure_groups (account_group_id, transaction_category_code, transaction_type_code, interest_rate)
VALUES ('DEFAULT', '0001', '01', 15.99);
