create TABLE USERS (
    id serial primary key,
    name varchar(100),
    email varchar(100) unique,
    password varchar(100),
    roleId INTEGER DEFAULT 1,
    active boolean
)