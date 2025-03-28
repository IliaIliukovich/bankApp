-- liquibase formatted sql
-- changeset ilia.iliukovich:001

CREATE TABLE IF NOT EXISTS manager (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(150) NULL,
    last_name VARCHAR(150) NULL,
    status VARCHAR(150) NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS client (
    id VARCHAR(45) NOT NULL,
    status VARCHAR(150) NULL,
    tax_code VARCHAR(150) NULL,
    first_name VARCHAR(150) NULL,
    last_name VARCHAR(150) NULL,
    email VARCHAR(45) NULL,
    address VARCHAR(250) NULL,
    phone VARCHAR(45) NULL,
    manager_id INT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_manager1
    FOREIGN KEY (manager_id)
    REFERENCES manager (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS account (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NULL,
    type VARCHAR(150) NULL,
    status VARCHAR(150) NULL,
    balance DECIMAL(15,2) NULL,
    currency_code VARCHAR(3) NULL,
    client_id VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_account_client
    FOREIGN KEY (client_id)
    REFERENCES client (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS product (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NULL,
    status VARCHAR(150) NULL,
    currency_code VARCHAR(150) NULL,
    interest_rate DECIMAL(15,2) NULL,
    limit_amount DECIMAL(15,2) NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS agreement (
    id VARCHAR(255) NOT NULL,
    interest_rate DECIMAL(15,2) NULL,
    status VARCHAR(150) NULL,
    sum DECIMAL(15,2) NULL,
    account_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_agreements_account1
    FOREIGN KEY (account_id)
    REFERENCES account (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT fk_agreements_product1
    FOREIGN KEY (product_id)
    REFERENCES product (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS transaction (
    id VARCHAR(36) NOT NULL,
    type VARCHAR(150) NULL,
    amount DECIMAL(15,2) NULL,
    description VARCHAR(150) NULL,
    status VARCHAR(45) NULL,
    debit_account_id INT NOT NULL,
    credit_account_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_transaction_account1
    FOREIGN KEY (debit_account_id)
    REFERENCES account (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT fk_transaction_account2
    FOREIGN KEY (credit_account_id)
    REFERENCES account (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS app_user (
    id VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(45) NOT NULL,
    client_id VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_app_user_client1
    FOREIGN KEY (client_id)
    REFERENCES client (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS card (
    id VARCHAR(45) NOT NULL,
    card_type VARCHAR(45) NULL,
    card_number VARCHAR(45) NULL,
    card_holder VARCHAR(45) NULL,
    cvv INT NULL,
    expiry_date VARCHAR(45) NULL,
    account_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_card_account1
    FOREIGN KEY (account_id)
    REFERENCES account (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);