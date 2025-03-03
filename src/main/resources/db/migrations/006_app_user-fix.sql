-- liquibase formatted sql
-- changeset ilia.iliukovich:006

SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE app_user
MODIFY COLUMN client_id VARCHAR(36) NULL;

SET FOREIGN_KEY_CHECKS = 1;
