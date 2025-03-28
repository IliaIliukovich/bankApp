-- liquibase formatted sql
-- changeset ilia.iliukovich:007

ALTER TABLE app_user
ADD COLUMN refresh_token varchar(255);

ALTER TABLE app_user
    ADD UNIQUE (email);