create table levels(
levels_id int primary key,
levels_name varchar(10)
);

create table worker (
	id serial primary key,
	name varchar not null check (length(name::text) >= 2 AND length(name::text) <= 1000),
	birthday date not null check (extract(year from birthday)>1900),
	levels_id integer not null,
	salary integer not null check (salary >=100 AND salary <=100000)
);

alter table worker add constraint worker_levels_fk foreign key (levels_id) references levels(levels_id);

