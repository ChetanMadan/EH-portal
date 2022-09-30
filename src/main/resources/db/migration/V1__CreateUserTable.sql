create TABLE USERS (
    id serial primary key,
    name varchar(100),
    email varchar(100) unique,
    password varchar(100),
    address varchar(500),
    dob TIMESTAMP,
    status INTEGER,
    phnumber varchar(10),
    location varchar(20),
    roleId INTEGER DEFAULT 1,
    lastsos TIMESTAMP,
    active boolean
)