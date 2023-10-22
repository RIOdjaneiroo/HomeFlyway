create table profiles (
worker_id bigint primary key,
second_name varchar (100),
adres varchar(255),
description varchar(255),
sex varchar(25)
);

alter table profiles
add constraint worker_profiles_fk
foreign key (worker_id)
references worker(id) on delete cascade;

