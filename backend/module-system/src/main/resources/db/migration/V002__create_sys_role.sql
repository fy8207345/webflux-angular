create table sys_role
(
    id          varchar(64) primary key,
    name        varchar(64) unique,
    code        varchar(64) unique,
    create_by   varchar(64),
    update_by   varchar(64),
    create_date datetime,
    update_date datetime,
    version     int
) engine = innodb
  charset = utf8mb4
  collate = utf8mb4_0900_ai_ci;
insert into sys_role
values ('sys_role_admin', '管理员', 'ROLE_ADMIN', 'admin', 'admin', '2021-02-01 16:36:09', '2021-02-01 16:36:09', 0);
