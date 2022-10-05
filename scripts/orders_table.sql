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
alter sequence orders_id_seq increment 1200;
grant all on Orders to sports_db_admin;