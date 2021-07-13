create table TBL_USERS
(
    USER_ID    SERIAL PRIMARY KEY,
    USER_EMAIL varchar(255),
    USER_NAME  varchar(255)
);
insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('FlyWay@mail.ru', 'FlyWayUser');