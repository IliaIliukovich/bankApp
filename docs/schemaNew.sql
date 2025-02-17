create database bankapp;
use bankapp;
desc bankapp.manager;
insert into bankapp.manager(id,first_name,last_name,status) value
(1, "Schmidt", "Hans", 0);
select * from bankapp.manager;
insert into bankapp.manager(id,first_name,last_name,status) value
(2, "Schmidt", "Hans", 1);