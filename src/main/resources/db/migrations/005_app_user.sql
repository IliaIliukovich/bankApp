-- liquibase formatted sql
-- changeset ilia.iliukovich:005

insert into app_user (id, email, password, role, client_id)
values  ( 'fbf170d8-0d7b-4d74-9385-2b0fcca15e4c', 'h.schmidt@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', 'b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0'),
        ( 'cfa9822d-db69-461c-8b72-196351fd8a0c', 'a.mueller@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT','20980395-20d0-4ea8-8e4b-de2252a028eb'),
        ( '2fcc9441-e0de-46f2-a7bc-2f8a016d9f95', 'p.klein@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', '9cd1b6c5-06d7-4f6c-9959-3856b1b51045'),
        ( '83e06394-ab79-4721-bc63-1acff888e01d', 'm.schneider@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', '5e659d3c-3925-457d-ae41-ca69001fb11c'),
        ('c7b46896-4a7c-43bd-b7b0-d211e21b1d79',  'l.fischer@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', 'b03dbcfc-d047-49a7-acbb-f3b1329e1fee'),
        ( 'd22431e8-d809-4a2b-9b84-d0af5b347d21', 's.weber@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', 'bb10eec7-e408-4f11-9603-ec3c4a371512'),
        ( '68fd9a8f-870f-4fe2-ab6a-acccf4fb99fb', 'm.meyer@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', '7d1388c1-04df-4473-b15b-fe614594aa3c'),
        ( 'a99966b9-6c8e-43c9-b2d1-15405be0a883', 'j.wagner@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', '3d18c400-a7b3-4bb1-bb29-8f566a43eca1'),
        ('6b3a5b63-e459-4cb4-bbef-55fb0a5ef026',  'p.becker@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', 'd07ec73f-702a-479c-a930-f51596d9b899'),
        ('a7280be7-4013-4afb-ac2d-b261304c99d0',  'c.hoffmann@example.com', '$2a$10$cHN5hrbf0UOmkrIT24VO1ep6fFpc4gxLRdR/YBy3IEVkF7mJELElS', 'CLIENT', 'f6bc3ef4-4894-42f2-9c7b-66b9a8b80c7c');
