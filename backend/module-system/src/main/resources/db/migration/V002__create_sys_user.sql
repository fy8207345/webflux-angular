create table sys_user (
    id int auto_increment primary key,
    account varchar(32) unique,
    password varchar(128),
    status varchar(16),
    nick_name varchar(32),
    avatar varchar(255),
    email varchar(64),
    mobile varchar(16),
    gender varchar(16),
    create_by varchar(64),
    update_by varchar(64),
    create_date datetime,
    update_date datetime,
    version int
) engine = innodb charset = utf8mb4 collate = utf8mb4_0900_ai_ci;
insert into sys_user values (1, "admin", "$2a$10$rgQgaOJPiMecL7HnU75tGOTBFUFlQnc8xM122/DI5FVtSkCvwH2XG", "Enabled", "管理员", "logo.png", "2316696377@qq.com", "18392396424", "Male", "admin", "admin", "2021-02-01 16:36:09", "2021-02-01 16:36:09", 0);
