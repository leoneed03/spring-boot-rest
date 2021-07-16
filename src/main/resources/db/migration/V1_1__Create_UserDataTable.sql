create table TBL_USERS
(
    USER_ID    SERIAL PRIMARY KEY,
    USER_EMAIL varchar(255) UNIQUE NOT NULL,
    USER_NAME  varchar(255) NOT NULL
);
insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('FlyWay@mail.ru', 'FlyWayUser');