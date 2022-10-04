create table Orders
(
    ID             serial primary key,
    Date           date,
    Total_Due      varchar(10),
    status         varchar(10),
    Customer_ID    integer,
    Salesperson_ID integer
);

alter table Orders owner to sports_db_admin;
grant all on Orders to sports_db_admin;