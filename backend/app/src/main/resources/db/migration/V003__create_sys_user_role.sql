create table sys_user_role
(
    id          varchar(64) primary key,
    user_id     varchar(64) unique,
    role_id     varchar(64) unique,
    create_by   varchar(64),
    update_by   varchar(64),
    create_date datetime,
    update_date datetime,
    version     int
) engine = innodb
  charset = utf8mb4
  collate = utf8mb4_0900_ai_ci;
insert into sys_user_role
values ('sys_user_role_admin_admin', 'sys_user_admin', 'sys_role_admin', 'admin', 'admin', '2021-02-01 16:36:09', '2021-02-01 16:36:09', 0);
