insert into	 bankapp.client (id, last_name, first_name, status) values ("4ea37144-df60-4681-b796-760345166d39", "Schmidt", "Hans", 1);

-- ADD PRODUCTS
INSERT INTO bankapp.product (id, name, currency_code, interest_rate, limit_amount, status)
VALUES
    (1, 'Current Account', 1, 2.0, 1500.75, 1),
    (2, 'Credit Account', 0, 18.0, 5000.00, 1),
    (3, 'Business Credit', 2, 18.0, 20000.00, 0),
    (4, 'Savings Account', 1, 1.5, 7500.25, 1),
    (5, 'Student Account', 0, 0.5, 1200.00, 1),
    (6, 'Premium Deposit', 1, 2.8, 10000.00, 0),
    (7, 'Car Loan', 2, 7.0, 15000.00, 1),
    (8, 'Mortgage Loan', 1, 3.5, 250000.00, 0),
    (9, 'Business Loan', 2, 12.0, 50000.00, 0),
    (10, 'Investment Plan', 0, 5.0, 30000.00, 1),
    (11, 'Retirement Plan', 1, 3.0, 200000.00, 1),
    (12, 'Education Loan', 2, 5.5, 25000.00, 1),
    (13, 'Personal Loan', 1, 8.0, 10000.00, 1),
    (14, 'Corporate Investment', 2, 10.0, 150000.00, 0),
    (15, 'Fixed Deposit', 0, 4.0, 50000.00, 1);

