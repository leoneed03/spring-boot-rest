create table TBL_USERS
(
    USER_ID    SERIAL PRIMARY KEY,
    USER_EMAIL varchar(255) UNIQUE NOT NULL,
    USER_NAME  varchar(255)        NOT NULL
);

CREATE INDEX idx_tbl_users_name
    ON TBL_USERS (USER_NAME);

create table TBL_PROP
(
    PROP_ID SERIAL PRIMARY KEY,
    OWNER_USER_ID int,
    foreign key (OWNER_USER_ID) references TBL_USERS(USER_ID),
    PROP_DESCRIPTION varchar(255) NOT NULL
);

insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('user_1@mail.ru', 'User1');
insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('user_2@mail.ru', 'User2');
insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('user_3@mail.ru', 'User3');
insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('user_4@mail.ru', 'User4');
insert into TBL_USERS (USER_EMAIL, USER_NAME) values ('user_5@mail.ru', 'User5');

insert into TBL_PROP (OWNER_USER_ID, PROP_DESCRIPTION)
select USER_ID, 'keys' from TBL_USERS where USER_EMAIL = 'user_3@mail.ru';

insert into TBL_PROP (OWNER_USER_ID, PROP_DESCRIPTION)
select USER_ID, 'phone' from TBL_USERS where USER_EMAIL = 'user_2@mail.ru';


insert into TBL_PROP (OWNER_USER_ID, PROP_DESCRIPTION)
select USER_ID, 'car' from TBL_USERS where USER_EMAIL = 'user_3@mail.ru';

insert into TBL_PROP (OWNER_USER_ID, PROP_DESCRIPTION)
select USER_ID, 'car' from TBL_USERS where USER_EMAIL = 'user_5@mail.ru';

select (PROP_ID, PROP_DESCRIPTION) from TBL_PROP inner join TBL_USERS TU on TBL_PROP.OWNER_USER_ID = TU.USER_ID where USER_EMAIL = 'user_3@mail.ru';