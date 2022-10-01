dropdb sports_db_populated --force

createdb sports_db_populated --owner=sports_db_admin

psql -f "./scripts/customers_table.sql" sports_db_populated
psql -c "COPY Customers(ID,First_Name,Last_Name,Email,Phone,Address,City,State,Zipcode) \
FROM '$(pwd)/sport_db/Customer.csv' \
DELIMITER ',' \
CSV HEADER;" sports_db_populated

psql -f "./scripts/orders_table.sql" sports_db_populated
psql -c "COPY Orders(ID,Date,Total_Due,Status,Customer_ID) \
FROM '$(pwd)/sport_db/Order.csv' \
DELIMITER ',' \
CSV HEADER;" sports_db_populated
