create table project (
id serial primary key,
project_name varchar (255),
client_id bigint not null,
start_date date not null,
finish_date date not null
);

alter table project add constraint project_client_fk foreign key (client_id)references client(id);
