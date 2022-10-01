create table Orders
(
    ID             integer primary key,
    Date           date,
    Total_Due      varchar(10),
    status         varchar(10),
    Customer_ID    integer,
    Salesperson_ID integer
);


grant all on Orders to sports_db_admin;