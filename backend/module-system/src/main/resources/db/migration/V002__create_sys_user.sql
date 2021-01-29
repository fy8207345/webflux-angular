create table sys_user (
    id int auto_increment primary key,
    account varchar(32) unique,
    password varchar(32),
    salt varchar(16),
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
) engine = innodb;
