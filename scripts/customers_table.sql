create table Customers
(
    ID         integer primary key,
    first_name varchar(20),
    last_name  varchar(20),
    email      varchar(50),
    phone      varchar(20),
    address    varchar(50),
    city       varchar(20),
    state      varchar(10),
    zipcode    varchar(10)
);

grant all on Customers to sports_db_admin;