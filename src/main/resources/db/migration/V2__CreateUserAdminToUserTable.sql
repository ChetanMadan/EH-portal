create TABLE admintouser (
    adminid int not null,
    assigneeids INTEGER[],
    seniorid int,
    CONSTRAINT adminfk
    FOREIGN KEY(adminid) REFERENCES USERS(id)
)