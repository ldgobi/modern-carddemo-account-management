-- Sample Data for Card Demo Application
-- This file contains consistent sample data across all tables with proper referential integrity

-- =====================================================
-- CUSTOMERS
-- =====================================================
INSERT INTO customers (customer_id, first_name, middle_name, last_name, ssn, date_of_birth, fico_score, 
                       address_line1, address_line2, city, state_code, zip_code, country_code, 
                       phone_number1, phone_number2, government_issued_id, eft_account_id, 
                       primary_card_holder_indicator, created_at, updated_at)
VALUES
-- Customer 1: Primary cardholder with excellent credit
(1001, 'John', 'Michael', 'Smith', '123456789', '1985-03-15', 780, 
 '123 Main Street', 'Apt 4B', 'New York', 'NY', '10001', 'USA', 
 '212-555-0101', '212-555-0102', 'DL-NY-12345678', 'EFT1001', 
 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Customer 2: Joint account holder with good credit
(1002, 'Sarah', 'Anne', 'Smith', '234567890', '1987-07-22', 720, 
 '123 Main Street', 'Apt 4B', 'New York', 'NY', '10001', 'USA', 
 '212-555-0103', NULL, 'DL-NY-87654321', 'EFT1002', 
 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Customer 3: Single account holder with fair credit
(1003, 'Michael', 'James', 'Johnson', '345678901', '1990-11-08', 650, 
 '456 Oak Avenue', NULL, 'Los Angeles', 'CA', '90001', 'USA', 
 '310-555-0201', '310-555-0202', 'DL-CA-11223344', 'EFT1003', 
 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Customer 4: Business account primary holder
(1004, 'Emily', 'Rose', 'Davis', '456789012', '1982-05-30', 800, 
 '789 Business Blvd', 'Suite 200', 'Chicago', 'IL', '60601', 'USA', 
 '312-555-0301', '312-555-0302', 'DL-IL-55667788', 'EFT1004', 
 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Customer 5: Recent graduate with limited credit history
(1005, 'David', NULL, 'Martinez', '567890123', '1998-09-12', 620, 
 '321 College Road', 'Apt 15', 'Austin', 'TX', '73301', 'USA', 
 '512-555-0401', NULL, 'DL-TX-99887766', NULL, 
 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- ACCOUNTS
-- =====================================================
INSERT INTO accounts (account_id, active_status, current_balance, credit_limit, cash_credit_limit, 
                      open_date, expiration_date, reissue_date, current_cycle_credit, current_cycle_debit, 
                      group_id, customer_id, created_at, updated_at)
VALUES
-- Account 1: Primary account for Customer 1001 (John Smith)
(2001, 'Y', 1250.50, 10000.00, 2000.00, 
 '2020-01-15', '2027-01-31', '2023-12-01', 500.00, 1750.50, 
 'PREMIUM', 1001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Account 2: Joint account shared by Customers 1001 and 1002
(2002, 'Y', 3456.75, 15000.00, 3000.00, 
 '2019-06-20', '2026-06-30', NULL, 1200.00, 4656.75, 
 'PREMIUM', 1001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Account 3: Account for Customer 1003 (Michael Johnson)
(2003, 'Y', 890.25, 5000.00, 1000.00, 
 '2021-03-10', '2028-03-31', NULL, 250.00, 1140.25, 
 'STANDARD', 1003, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Account 4: Business account for Customer 1004 (Emily Davis)
(2004, 'Y', 5678.90, 25000.00, 5000.00, 
 '2018-09-05', '2025-09-30', '2022-08-15', 3500.00, 9178.90, 
 'BUSINESS', 1004, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Account 5: Starter account for Customer 1005 (David Martinez)
(2005, 'Y', 432.10, 2000.00, 400.00, 
 '2023-02-28', '2030-02-28', NULL, 150.00, 582.10, 
 'STANDARD', 1005, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Account 6: Inactive account for Customer 1003
(2006, 'N', 0.00, 3000.00, 600.00, 
 '2017-05-12', '2024-05-31', NULL, 0.00, 0.00, 
 'STANDARD', 1003, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- CARDS
-- =====================================================
INSERT INTO cards (card_number, account_id, customer_id, card_status, expiration_date, created_at, updated_at)
VALUES
-- Cards for Account 2001 (John Smith's primary account)
('4111111111111001', 2001, 1001, 'A', '2027-01-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cards for Account 2002 (Joint account)
('4111111111111002', 2002, 1001, 'A', '2026-06-30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111003', 2002, 1002, 'A', '2026-06-30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cards for Account 2003 (Michael Johnson)
('4111111111111004', 2003, 1003, 'A', '2028-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cards for Account 2004 (Emily Davis - Business)
('4111111111111005', 2004, 1004, 'A', '2025-09-30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cards for Account 2005 (David Martinez)
('4111111111111006', 2005, 1005, 'A', '2030-02-28', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Inactive card for Account 2006
('4111111111111007', 2006, 1003, 'C', '2024-05-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- CARD_XREF
-- =====================================================
INSERT INTO card_xref (card_number, customer_id, account_id, created_at, updated_at)
VALUES
-- Cross-reference for all active cards
('4111111111111001', 1001, 2001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111002', 1001, 2002, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111003', 1002, 2002, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111004', 1003, 2003, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111005', 1004, 2004, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111006', 1005, 2005, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4111111111111007', 1003, 2006, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- DISCLOSURE_GROUPS
-- =====================================================
INSERT INTO disclosure_groups (account_group_id, transaction_category_code, transaction_type_code, interest_rate, created_at, updated_at)
VALUES
-- Premium account rates
('PREMIUM', '0001', '01', 12.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Purchases
('PREMIUM', '0002', '01', 14.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Cash advances
('PREMIUM', '0003', '01', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Payments

-- Standard account rates
('STANDARD', '0001', '01', 15.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Purchases
('STANDARD', '0002', '01', 19.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Cash advances
('STANDARD', '0003', '01', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Payments

-- Business account rates
('BUSINESS', '0001', '01', 11.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Purchases
('BUSINESS', '0002', '01', 13.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Cash advances
('BUSINESS', '0003', '01', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);  -- Payments

-- =====================================================
-- TRANSACTIONS
-- =====================================================
INSERT INTO transactions (transaction_id, card_number, type_code, category_code, source, description, 
                         amount, merchant_id, merchant_name, merchant_city, merchant_zip, 
                         original_timestamp, processed_timestamp, created_at, updated_at)
VALUES
-- Transactions for Card 4111111111111001 (John Smith - Account 2001)
('TX00000000000001', '4111111111111001', '01', '0001', 'POS', 'Grocery Store Purchase', 
 125.50, 9001, 'Whole Foods Market', 'New York', '10001', 
 TIMESTAMP '2024-11-01 10:30:00', TIMESTAMP '2024-11-01 10:30:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000002', '4111111111111001', '01', '0001', 'ONLINE', 'Amazon Purchase', 
 89.99, 9002, 'Amazon.com', 'Seattle', '98101', 
 TIMESTAMP '2024-11-02 14:15:00', TIMESTAMP '2024-11-02 14:15:03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000003', '4111111111111001', '01', '0001', 'POS', 'Restaurant Dinner', 
 67.25, 9003, 'Italian Bistro', 'New York', '10001', 
 TIMESTAMP '2024-11-03 19:45:00', TIMESTAMP '2024-11-03 19:45:02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Transactions for Card 4111111111111002 (John Smith - Joint Account 2002)
('TX00000000000004', '4111111111111002', '01', '0001', 'POS', 'Gas Station', 
 55.00, 9004, 'Shell Gas Station', 'New York', '10002', 
 TIMESTAMP '2024-11-04 08:20:00', TIMESTAMP '2024-11-04 08:20:03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000005', '4111111111111002', '01', '0001', 'ONLINE', 'Streaming Service', 
 15.99, 9005, 'Netflix', 'Los Gatos', '95032', 
 TIMESTAMP '2024-11-05 00:01:00', TIMESTAMP '2024-11-05 00:01:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Transactions for Card 4111111111111003 (Sarah Smith - Joint Account 2002)
('TX00000000000006', '4111111111111003', '01', '0001', 'POS', 'Department Store', 
 245.75, 9006, 'Macys', 'New York', '10001', 
 TIMESTAMP '2024-11-05 15:30:00', TIMESTAMP '2024-11-05 15:30:04', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000007', '4111111111111003', '01', '0001', 'POS', 'Coffee Shop', 
 12.50, 9007, 'Starbucks', 'New York', '10001', 
 TIMESTAMP '2024-11-06 07:45:00', TIMESTAMP '2024-11-06 07:45:02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Transactions for Card 4111111111111004 (Michael Johnson - Account 2003)
('TX00000000000008', '4111111111111004', '01', '0001', 'POS', 'Electronics Store', 
 299.99, 9008, 'Best Buy', 'Los Angeles', '90001', 
 TIMESTAMP '2024-11-06 16:20:00', TIMESTAMP '2024-11-06 16:20:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000009', '4111111111111004', '01', '0002', 'ATM', 'Cash Withdrawal', 
 200.00, 9009, 'Bank of America ATM', 'Los Angeles', '90001', 
 TIMESTAMP '2024-11-07 12:00:00', TIMESTAMP '2024-11-07 12:00:03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Transactions for Card 4111111111111005 (Emily Davis - Business Account 2004)
('TX00000000000010', '4111111111111005', '01', '0001', 'ONLINE', 'Office Supplies', 
 456.80, 9010, 'Office Depot', 'Boca Raton', '33487', 
 TIMESTAMP '2024-11-07 09:30:00', TIMESTAMP '2024-11-07 09:30:04', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000011', '4111111111111005', '01', '0001', 'POS', 'Business Travel Hotel', 
 850.00, 9011, 'Marriott Hotels', 'Chicago', '60601', 
 TIMESTAMP '2024-11-08 16:00:00', TIMESTAMP '2024-11-08 16:00:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000012', '4111111111111005', '01', '0001', 'POS', 'Client Dinner', 
 275.40, 9012, 'The Capital Grille', 'Chicago', '60601', 
 TIMESTAMP '2024-11-08 20:30:00', TIMESTAMP '2024-11-08 20:30:03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Transactions for Card 4111111111111006 (David Martinez - Account 2005)
('TX00000000000013', '4111111111111006', '01', '0001', 'POS', 'Grocery Store', 
 78.45, 9013, 'H-E-B', 'Austin', '73301', 
 TIMESTAMP '2024-11-08 11:15:00', TIMESTAMP '2024-11-08 11:15:02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000014', '4111111111111006', '01', '0001', 'ONLINE', 'Textbook Purchase', 
 125.00, 9014, 'Chegg', 'Santa Clara', '95054', 
 TIMESTAMP '2024-11-09 13:45:00', TIMESTAMP '2024-11-09 13:45:04', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Payment transactions (type_code 01, category_code 0003)
('TX00000000000015', '4111111111111001', '01', '0003', 'PAYMENT', 'Monthly Payment', 
 -500.00, NULL, NULL, NULL, NULL, 
 TIMESTAMP '2024-11-01 00:00:00', TIMESTAMP '2024-11-01 00:00:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000016', '4111111111111002', '01', '0003', 'PAYMENT', 'Monthly Payment', 
 -1200.00, NULL, NULL, NULL, NULL, 
 TIMESTAMP '2024-11-01 00:00:00', TIMESTAMP '2024-11-01 00:00:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000017', '4111111111111004', '01', '0003', 'PAYMENT', 'Monthly Payment', 
 -250.00, NULL, NULL, NULL, NULL, 
 TIMESTAMP '2024-11-01 00:00:00', TIMESTAMP '2024-11-01 00:00:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000018', '4111111111111005', '01', '0003', 'PAYMENT', 'Monthly Payment', 
 -3500.00, NULL, NULL, NULL, NULL, 
 TIMESTAMP '2024-11-01 00:00:00', TIMESTAMP '2024-11-01 00:00:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('TX00000000000019', '4111111111111006', '01', '0003', 'PAYMENT', 'Monthly Payment', 
 -150.00, NULL, NULL, NULL, NULL, 
 TIMESTAMP '2024-11-01 00:00:00', TIMESTAMP '2024-11-01 00:00:05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =====================================================
-- TRANSACTION_CATEGORY_BALANCES
-- =====================================================
INSERT INTO transaction_category_balances (account_id, type_code, category_code, balance, created_at, updated_at)
VALUES
-- Account 2001 (John Smith)
(2001, '01', '0001', 1750.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Purchases balance
(2001, '01', '0002', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- Cash advances balance
(2001, '01', '0003', -500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Payments (negative)

-- Account 2002 (Joint account)
(2002, '01', '0001', 4656.75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Purchases balance
(2002, '01', '0002', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- Cash advances balance
(2002, '01', '0003', -1200.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Payments (negative)

-- Account 2003 (Michael Johnson)
(2003, '01', '0001', 940.25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Purchases balance
(2003, '01', '0002', 200.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Cash advances balance
(2003, '01', '0003', -250.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Payments (negative)

-- Account 2004 (Emily Davis - Business)
(2004, '01', '0001', 9178.90, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Purchases balance
(2004, '01', '0002', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- Cash advances balance
(2004, '01', '0003', -3500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Payments (negative)

-- Account 2005 (David Martinez)
(2005, '01', '0001', 582.10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Purchases balance
(2005, '01', '0002', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- Cash advances balance
(2005, '01', '0003', -150.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Payments (negative)

-- Account 2006 (Inactive)
(2006, '01', '0001', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- Purchases balance
(2006, '01', '0002', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),     -- Cash advances balance
(2006, '01', '0003', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);     -- Payments (negative)
