-- MySQL Script generated by MySQL Workbench
-- Sat Feb 15 12:01:35 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bankApp
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bankApp` ;

-- -----------------------------------------------------
-- Schema bankApp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bankApp` DEFAULT CHARACTER SET utf8 ;
USE `bankApp` ;

-- -----------------------------------------------------
-- Table `bankApp`.`manager`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`manager` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`manager` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(150) NULL,
    `last_name` VARCHAR(150) NULL,
    `status` VARCHAR(150) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`client` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`client` (
    `id` VARCHAR(45) NOT NULL,
    `status` VARCHAR(150) NULL,
    `tax_code` VARCHAR(150) NULL,
    `first_name` VARCHAR(150) NULL,
    `last_name` VARCHAR(150) NULL,
    `email` VARCHAR(45) NULL,
    `address` VARCHAR(250) NULL,
    `phone` VARCHAR(45) NULL,
    `manager_id` INT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_client_manager1_idx` (`manager_id` ASC) VISIBLE,
    CONSTRAINT `fk_client_manager1`
    FOREIGN KEY (`manager_id`)
    REFERENCES `bankApp`.`manager` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`account` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`account` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(150) NULL,
    `type` VARCHAR(150) NULL,
    `status` VARCHAR(150) NULL,
    `balance` DECIMAL(15,2) NULL,
    `currency_code` VARCHAR(3) NULL,
    `client_id` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_account_client_idx` (`client_id` ASC) VISIBLE,
    CONSTRAINT `fk_account_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `bankApp`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`product` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`product` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(150) NULL,
    `status` VARCHAR(150) NULL,
    `currency_code` VARCHAR(150) NULL,
    `interest_rate` DECIMAL(15,2) NULL,
    `limit_amount` DECIMAL(15,2) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`agreement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`agreement` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`agreement` (
    `id` VARCHAR(255) NOT NULL,
    `interest_rate` DECIMAL(15,2) NULL,
    `status` VARCHAR(150) NULL,
    `sum` DECIMAL(15,2) NULL,
    `account_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_agreements_account1_idx` (`account_id` ASC) VISIBLE,
    INDEX `fk_agreements_product1_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `fk_agreements_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `bankApp`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_agreements_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `bankApp`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`transaction` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`transaction` (
    `id` VARCHAR(36) NOT NULL,
    `type` VARCHAR(150) NULL,
    `amount` DECIMAL(15,2) NULL,
    `description` VARCHAR(150) NULL,
    `status` VARCHAR(45) NULL,
    `debit_account_id` INT NOT NULL,
    `credit_account_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_transaction_account1`
    FOREIGN KEY (`debit_account_id`)
    REFERENCES `bankApp`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_transaction_account2`
    FOREIGN KEY (`credit_account_id`)
    REFERENCES `bankApp`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`app_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`app_user` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`app_user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `role` VARCHAR(45) NOT NULL,
    `client_id` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_app_user_client1_idx` (`client_id` ASC) VISIBLE,
    CONSTRAINT `fk_app_user_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `bankApp`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankApp`.`card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bankApp`.`card` ;

CREATE TABLE IF NOT EXISTS `bankApp`.`card` (
    `id` VARCHAR(45) NOT NULL,
    `card_type` VARCHAR(45) NULL,
    `card_number` VARCHAR(45) NULL,
    `card_holder` VARCHAR(45) NULL,
    `cvv` INT NULL,
    `expiry_date` VARCHAR(45) NULL,
    `account_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_card_account1_idx` (`account_id` ASC) VISIBLE,
    CONSTRAINT `fk_card_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `bankApp`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
