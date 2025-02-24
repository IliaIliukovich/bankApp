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
values  (1, "Current Account", "EUR", 0.0, 1000000.00, "ACTIVE"),
        (2, "Credit Account", "EUR", 18.0, 5000.00, "INACTIVE"),
        (3, "Business Credit", "GBP", 18.0, 2000000.00, "ACTIVE"),
        (4, "Savings Account", "GBP", 1.5, 1000000.00, "INACTIVE"),
        (5, "Student Account", "EUR", 0.5, 100000.00, "INACTIVE"),
        (6, "Premium Deposit", "USD", 2.8, 20000000.00, "ACTIVE"),
        (7, "Car Loan", "GBP", 7.0, 15000.00, "INACTIVE"),
        (8, "Mortgage Loan", "USD", 3.5, 250000.00, "ACTIVE"),
        (9, "Business Loan", "GBP", 12.0, 50000.00, "ACTIVE"),
        (10, "Investment Plan", "EUR", 5.0, 30000.00, "INACTIVE"),
        (11, "Retirement Plan", "USD", 3.0, 200000.00, "INACTIVE"),
        (12, "Education Loan", "GBP", 5.5, 25000.00, "INACTIVE"),
        (13, "Personal Loan", "USD", 8.0, 10000.00, "INACTIVE"),
        (14, "Corporate Investment", "GBP", 10.0, 150000.00, "ACTIVE"),
        (15, "Fixed Deposit", "EUR", 4.0, 500000.00, "INACTIVE");

insert into	 account (id, name, type, status, balance, currency_code, client_id)
values  (1, "DE88370400440532013021", "DEBIT_CARD", "ACTIVE", 1500.75, "EUR", "b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0"),
        (2, "DE88370400440532013088", "DEBIT_CARD", "ACTIVE", 10200.00, "EUR", "20980395-20d0-4ea8-8e4b-de2252a028eb"),
        (3, "DE88370400440532013545", "CREDIT_CARD", "BLOCKED", -5000.50, "EUR", "9cd1b6c5-06d7-4f6c-9959-3856b1b51045"),
        (4, "DE88370400440532012111", "DEBIT_CARD", "INACTIVE", 200.00, "GBP", "5e659d3c-3925-457d-ae41-ca69001fb11c"),
        (5, "DE88370400440532016543", "CREDIT_CARD", "ACTIVE", -1200.00, "EUR", "b03dbcfc-d047-49a7-acbb-f3b1329e1fee"),
        (6, "DE88370400440532019876", "DEBIT_CARD", "BLOCKED", 500.25, "EUR", "bb10eec7-e408-4f11-9603-ec3c4a371512"),
        (7, "DE88370400440532011254", "SAVINGS", "INACTIVE", 2500.00, "GBP", "7d1388c1-04df-4473-b15b-fe614594aa3c"),
        (8, "DE88370400440532015555", "CHECKING", "ACTIVE", 75.90, "EUR", "20980395-20d0-4ea8-8e4b-de2252a028eb"),
        (9, "DE88370400440532018634", "CHECKING", "BLOCKED", 0.00, "EUR", "d07ec73f-702a-479c-a930-f51596d9b899"),
        (10, "DE88370400440532010643", "LOAN", "ACTIVE", -300.40, "GBP", "20980395-20d0-4ea8-8e4b-de2252a028eb");

insert into agreement (id, interest_rate, status, sum, account_id, product_id)
values  ("5fd88e18-aff9-4358-8ab0-01a3bfa19a39", 0.0, "ACTIVE", 1000000.00, 1, 1),
        ("796cf54b-d1a8-4b76-a8aa-874f806d08ce", 0.0, "ACTIVE", 1000000.00, 2, 1),
        ("c4975145-932b-4360-8dec-ee4c88f3fcc1", 18.0, "ACTIVE", 5000.00, 3, 2),
        ("84fe027d-3db2-4a0b-ad0c-29168b2f3367", 1.5, "ACTIVE", 1000000.00, 4, 4),
        ("189c2b8a-4ca7-4177-bd83-8769da1e13d4", 18.0, "ACTIVE", 5000.00, 5, 2),
        ("52884483-8566-47f4-a43f-cc5eb49264f5", 0.0, "ACTIVE", 1000000.00, 6, 1),
        ("91acf807-f400-4867-aedc-5a071bf64fc3", 1.5, "ACTIVE", 1000000.00, 7, 4),
        ("8b1f88d4-4ea7-472c-a082-df5585ce2ebc", 4.0, "ACTIVE", 500000.00, 8, 15),
        ("b9741ef2-d9db-41cf-9fc3-8992c8f22e99", 4.0, "ACTIVE", 500000.00, 9, 15),
        ("a680d9e7-e579-4ef4-9ecf-18ccdc7763f1", 12.0, "ACTIVE", 50000.00, 10, 9);

insert into transaction (id, type, amount, description, status, debit_account_id, credit_account_id)
values  ("4ea37144-df60-4681-b796-760345166d39", "PAYMENT", 12.0, "description", "APPROVED", 1, 2),
        ("a75c7e4b-b82d-4cb6-b217-381481609065", "PAYMENT", 23.0, "description", "PENDING", 1, 3),
        ("8bdf4491-9b25-4194-a67d-d9929f210d37", "PAYMENT", 200.0, "description", "NEW", 2, 3),
        ("40d4b66c-c7df-44aa-b362-22b70ffc6df3", "PAYMENT", 22.0, "description", "APPROVED", 1, 2),
        ("34511ef8-2968-478c-8f0f-4d2bcb0e9a44", "PAYMENT", 300.4, "description", "APPROVED", 4, 10),
        ("6e0a061a-a846-45b2-9960-f4b830890b2c", "TRANSFER", 100, "transfer between own accounts", "APPROVED", 2, 8);

insert into card (id, card_number, card_holder, cvv, expiry_date, account_id, card_type)
values  ("e5b1bf42-46a5-4fc2-9c69-6884c1a942ae", "1415 1532 7818 4133", "Hans Schmidt", 1, "03/29", 1, "MASTERCARD"),
        ("855ee7f8-b727-4aa4-bdf7-54c6428ce429", "2425 3542 9817 3234", "Anna Müller", 2, "01/27", 2, "VISA"),
        ("3922f2b5-732e-46ca-811f-753a18170018", "4326 1532 0814 2244", "Peter Klein", 201, "12/30", 3, "MASTERCARD"),
        ("6f00b07d-5b81-4a2c-bac5-3dde080832d7", "3215 2418 1439 5083", "Maria Schneider", 111, "03/28", 4, "VISA"),
        ("74b2a73e-82fa-42bc-953e-fcf571c3c8b4", "9734 7382 9341 3850", "Lukas Fischer", 15, "05/31", 5, "VISA"),
        ("36112d3f-65c9-4f85-a356-d2ed6c42da1a", "4411 0522 7708 5132", "Sophie Weber", 321, "04/27", 6, "MASTERCARD");
