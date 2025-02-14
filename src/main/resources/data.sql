insert into	 client (id, last_name, first_name, tax_code, email, address, phone, status)
values  ("b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0","Schmidt", "Hans", "DE123456789", "h.schmidt@example.com", "Berlin, Germany", "+49 30 1234567", "ACTIVE"),
        ("20980395-20d0-4ea8-8e4b-de2252a028eb", "Müller", "Anna", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", "INACTIVE"),
        ("9cd1b6c5-06d7-4f6c-9959-3856b1b51045", "Klein", "Peter", "DE567890123", "p.klein@example.com", "Hamburg, Germany", "+49 40 6789012", "BLOCKED"),
        ("5e659d3c-3925-457d-ae41-ca69001fb11c", "Schneider", "Maria", "DE456789012", "m.schneider@example.com", "Frankfurt, Germany", "+49 69 1234567", "INACTIVE"),
        ("b03dbcfc-d047-49a7-acbb-f3b1329e1fee", "Fischer", "Lukas", "DE234567890", "l.fischer@example.com", "Stuttgart, Germany", "+49 711 9876543", "INACTIVE"),
        ("bb10eec7-e408-4f11-9603-ec3c4a371512", "Weber", "Sophie", "DE890123456", "s.weber@example.com", "Cologne, Germany", "+49 221 4567890", "ACTIVE"),
        ("7d1388c1-04df-4473-b15b-fe614594aa3c", "Meyer", "Max", "DE345678901", "m.meyer@example.com", "Düsseldorf, Germany", "+49 211 2345678", "BLOCKED"),
        ("3d18c400-a7b3-4bb1-bb29-8f566a43eca1", "Wagner", "Julia", "DE678901234", "j.wagner@example.com", "Dresden, Germany", "+49 351 8765432", "ACTIVE"),
        ("d07ec73f-702a-479c-a930-f51596d9b899", "Becker", "Anna", "DE789012345", "p.becker@example.com", "Leipzig, Germany", "+49 341 7654321", "INACTIVE"),
        ("f6bc3ef4-4894-42f2-9c7b-66b9a8b80c7c", "Hoffmann", "Clara", "DE012345678", "c.hoffmann@example.com", "Bremen, Germany", "+49 421 1234567", "ACTIVE");

insert into manager (id,first_name,last_name,status)
values  (1, "Hans", "Schmidt", 0),
        (2, "Anna", "Müller", 1),
        (3, "Peter", "Weber", 1),
        (4, "Claudia", "Fischer", 0),
        (5, "Thomas", "Meyer", 1),
        (6, "Sophia", "Wagner", 0),
        (7, "Stefan", "Becker", 1),
        (8, "Julia", "Schulz", 0),
        (9, "Markus", "Hoffmann", 1),
        (10, "Laura", "Lehmann", 0);

insert into transaction (id, type, amount, description, status, debit_account_id, credit_account_id)
values  ("4ea37144-df60-4681-b796-760345166d39", 0, 12.0, "description", 2, 1, 2),
        ("a75c7e4b-b82d-4cb6-b217-381481609065", 0, 23.0, "description", 1, 1, 3),
        ("8bdf4491-9b25-4194-a67d-d9929f210d37", 0, 200.0, "description", 0, 2, 3);

insert into product (id, name, currency_code, interest_rate, limit_amount, status)
values
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

insert into	 account (id, name, type, status, balance, currency_code)
values
    (1, 'John Doe',  'CHECKING', 'ACTIVE', 1500.75, 'USD'),
    (2, 'Alice Smith', 'SAVINGS', 'ACTIVE', 10200.00, 'EUR'),
    (3, 'Bob Johnson', 'LOAN', 'BLOCKED', -5000.50, 'USD'),
    (4, 'Emma Brown', 'DEBIT_CARD', 'INACTIVE', 200.00, 'GBP'),
    (5, 'David Wilson', 'CREDIT_CARD', 'ACTIVE', -1200.00, 'EUR'),
    (6, 'Sophia Martinez', 'CHECKING', 'BLOCKED', 500.25, 'USD'),
    (7, 'James Anderson', 'SAVINGS', 'INACTIVE', 2500.00, 'GBP'),
    (8, 'Olivia Taylor', 'OTHER', 'ACTIVE', 75.90, 'EUR'),
    (9, 'Michael White', 'DEBIT_CARD', 'BLOCKED', 0.00, 'USD'),
    (10, 'Emily Harris', 'CREDIT_CARD', 'ACTIVE', -300.40, 'GBP');