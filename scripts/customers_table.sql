create table Customers
(
    ID         serial primary key,
    first_name varchar(20),
    last_name  varchar(20),
    email      varchar(50),
    phone      varchar(20),
    address    varchar(50),
    city       varchar(20),
    state      varchar(10),
    zipcode    varchar(10)
);

alter table Customers owner to sports_db_admin;
grant all on Customers to sports_db_admin;