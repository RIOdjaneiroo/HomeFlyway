create table project_worker(
project_id bigint not null,
worker_id bigint not null,
primary key (project_id, worker_id),
foreign key (project_id) references project(id),
foreign key (worker_id) references worker(id)
);
