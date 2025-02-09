insert into	 bankapp.client (id, last_name, first_name, status) values ("4ea37144-df60-4681-b796-760345166d39", "Schmidt", "Hans", 1);

-- ADD PRODUCTS
INSERT INTO bankapp.product (id, name, currency_code, interest_rate, limit_amount, status)
VALUES
    (76034516639, 'Current Account', 1, 2.0, 1500.75, 1),
    (76034516640, 'Credit Account', 0, 18.0, 5000.00, 1),
    (76034516241, 'Business Credit', 2, 18.0, 20000.00, 0),
    (76034516642, 'Savings Account', 1, 1.5, 7500.25, 1),
    (76034516643, 'Student Account', 0, 0.5, 1200.00, 1),
    (76034516644, 'Premium Deposit', 1, 2.8, 10000.00, 0),
    (76034516645, 'Car Loan', 2, 7.0, 15000.00, 1),
    (76034516646, 'Mortgage Loan', 1, 3.5, 250000.00, 0),
    (76034516647, 'Business Loan', 2, 12.0, 50000.00, 0),
    (76034516648, 'Investment Plan', 0, 5.0, 30000.00, 1),
    (76034516649, 'Retirement Plan', 1, 3.0, 200000.00, 1),
    (76034516650, 'Education Loan', 2, 5.5, 25000.00, 1),
    (76034516651, 'Personal Loan', 1, 8.0, 10000.00, 1),
    (76034516652, 'Corporate Investment', 2, 10.0, 150000.00, 0),
    (76034516653, 'Fixed Deposit', 0, 4.0, 50000.00, 1);

