insert into manager (id,first_name,last_name,status)
values  (1, "Hans", "Schmidt", "ACTIVE"),
        (2, "Anna", "Müller", "INACTIVE"),
        (3, "Peter", "Weber", "INACTIVE"),
        (4, "Claudia", "Fischer", "ACTIVE"),
        (5, "Thomas", "Meyer", "INACTIVE"),
        (6, "Sophia", "Wagner", "ACTIVE"),
        (7, "Stefan", "Becker", "INACTIVE"),
        (8, "Julia", "Schulz", "ACTIVE"),
        (9, "Markus", "Hoffmann", "INACTIVE"),
        (10, "Laura", "Lehmann", "ACTIVE");

insert into	 client (id, last_name, first_name, tax_code, email, address, phone, status, manager_id)
values  ("b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0","Schmidt", "Hans", "DE123456789", "h.schmidt@example.com", "Berlin, Germany", "+49 30 1234567", "ACTIVE", 1),
        ("20980395-20d0-4ea8-8e4b-de2252a028eb", "Müller", "Anna", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", "INACTIVE", 1),
        ("9cd1b6c5-06d7-4f6c-9959-3856b1b51045", "Klein", "Peter", "DE567890123", "p.klein@example.com", "Hamburg, Germany", "+49 40 6789012", "BLOCKED", 1),
        ("5e659d3c-3925-457d-ae41-ca69001fb11c", "Schneider", "Maria", "DE456789012", "m.schneider@example.com", "Frankfurt, Germany", "+49 69 1234567", "INACTIVE", 2),
        ("b03dbcfc-d047-49a7-acbb-f3b1329e1fee", "Fischer", "Lukas", "DE234567890", "l.fischer@example.com", "Stuttgart, Germany", "+49 711 9876543", "INACTIVE", 3),
        ("bb10eec7-e408-4f11-9603-ec3c4a371512", "Weber", "Sophie", "DE890123456", "s.weber@example.com", "Cologne, Germany", "+49 221 4567890", "ACTIVE", 3),
        ("7d1388c1-04df-4473-b15b-fe614594aa3c", "Meyer", "Max", "DE345678901", "m.meyer@example.com", "Düsseldorf, Germany", "+49 211 2345678", "BLOCKED", 4),
        ("3d18c400-a7b3-4bb1-bb29-8f566a43eca1", "Wagner", "Julia", "DE678901234", "j.wagner@example.com", "Dresden, Germany", "+49 351 8765432", "ACTIVE", 5),
        ("d07ec73f-702a-479c-a930-f51596d9b899", "Becker", "Anna", "DE789012345", "p.becker@example.com", "Leipzig, Germany", "+49 341 7654321", "INACTIVE", null),
        ("f6bc3ef4-4894-42f2-9c7b-66b9a8b80c7c", "Hoffmann", "Clara", "DE012345678", "c.hoffmann@example.com", "Bremen, Germany", "+49 421 1234567", "ACTIVE", null);

insert into product (id, name, currency_code, interest_rate, limit_amount, status)
values  (1, "Current Account", "USD", 2.0, 1500.75, "ACTIVE"),
        (2, "Credit Account", "EUR", 18.0, 5000.00, "INACTIVE"),
        (3, "Business Credit", "GBP", 18.0, 20000.00, "ACTIVE"),
        (4, "Savings Account", "USD", 1.5, 7500.25, "INACTIVE"),
        (5, "Student Account", "EUR", 0.5, 1200.00, "INACTIVE"),
        (6, "Premium Deposit", "USD", 2.8, 10000.00, "ACTIVE"),
        (7, "Car Loan", "GBP", 7.0, 15000.00, "INACTIVE"),
        (8, "Mortgage Loan", "USD", 3.5, 250000.00, "ACTIVE"),
        (9, "Business Loan", "GBP", 12.0, 50000.00, "ACTIVE"),
        (10, "Investment Plan", "EUR", 5.0, 30000.00, "INACTIVE"),
        (11, "Retirement Plan", "USD", 3.0, 200000.00, "INACTIVE"),
        (12, "Education Loan", "GBP", 5.5, 25000.00, "INACTIVE"),
        (13, "Personal Loan", "USD", 8.0, 10000.00, "INACTIVE"),
        (14, "Corporate Investment", "GBP", 10.0, 150000.00, "ACTIVE"),
        (15, "Fixed Deposit", "EUR", 4.0, 50000.00, "INACTIVE");

insert into	 account (id, name, type, status, balance, currency_code, client_id)
values  (1, "DE88370400440532013021", "CHECKING", "ACTIVE", 1500.75, "USD", "b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0"),
        (2, "DE88370400440532013088", "SAVINGS", "ACTIVE", 10200.00, "EUR", "20980395-20d0-4ea8-8e4b-de2252a028eb"),
        (3, "DE88370400440532013545", "LOAN", "BLOCKED", -5000.50, "USD", "9cd1b6c5-06d7-4f6c-9959-3856b1b51045"),
        (4, "DE88370400440532012111", "DEBIT_CARD", "INACTIVE", 200.00, "GBP", "5e659d3c-3925-457d-ae41-ca69001fb11c"),
        (5, "DE88370400440532016543", "CREDIT_CARD", "ACTIVE", -1200.00, "EUR", "b03dbcfc-d047-49a7-acbb-f3b1329e1fee"),
        (6, "DE88370400440532019876", "CHECKING", "BLOCKED", 500.25, "USD", "bb10eec7-e408-4f11-9603-ec3c4a371512"),
        (7, "DE88370400440532011254", "SAVINGS", "INACTIVE", 2500.00, "GBP", "7d1388c1-04df-4473-b15b-fe614594aa3c"),
        (8, "DE88370400440532015555", "OTHER", "ACTIVE", 75.90, "EUR", "3d18c400-a7b3-4bb1-bb29-8f566a43eca1"),
        (9, "DE88370400440532018634", "DEBIT_CARD", "BLOCKED", 0.00, "USD", "d07ec73f-702a-479c-a930-f51596d9b899"),
        (10, "DE88370400440532010643", "CREDIT_CARD", "ACTIVE", -300.40, "GBP", "f6bc3ef4-4894-42f2-9c7b-66b9a8b80c7c");

insert into transaction (id, type, amount, description, status, debit_account_id, credit_account_id)
values  ("4ea37144-df60-4681-b796-760345166d39", "PAYMENT", 12.0, "description", "APPROVED", 1, 2),
        ("a75c7e4b-b82d-4cb6-b217-381481609065", "PAYMENT", 23.0, "description", "PENDING", 1, 3),
        ("8bdf4491-9b25-4194-a67d-d9929f210d37", "PAYMENT", 200.0, "description", "NEW", 2, 3);

insert into card (id, card_number, card_holder, cvv, expiry_date, account_id, card_type)
values  ("e5b1bf42-46a5-4fc2-9c69-6884c1a942ae", "1415 1532 7818 4133", "Hans Schmidt", 1, "03/29", 1, "MASTERCARD"),
        ("855ee7f8-b727-4aa4-bdf7-54c6428ce429", "2425 3542 9817 3234", "Anna Müller", 2, "01/27", 2, "VISA"),
        ("3922f2b5-732e-46ca-811f-753a18170018", "4326 1532 0814 2244", "Peter Klein", 201, "12/30", 3, "MASTERCARD"),
        ("6f00b07d-5b81-4a2c-bac5-3dde080832d7", "3215 2418 1439 5083", "Maria Schneider", 111, "03/28", 4, "VISA"),
        ("74b2a73e-82fa-42bc-953e-fcf571c3c8b4", "9734 7382 9341 3850", "Lukas Fischer", 15, "05/31", 5, "VISA"),
        ("36112d3f-65c9-4f85-a356-d2ed6c42da1a", "4411 0522 7708 5132", "Sophie Weber", 321, "04/27", 6, "MASTERCARD");
