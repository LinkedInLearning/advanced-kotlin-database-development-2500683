import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    connect()

    transaction {
        SchemaUtils.drop(OrdersTable)
        SchemaUtils.create(OrdersTable)
        OrdersTable.insert { row ->
            row[totalDue] = "370.86"
            row[customerId] = 789
            row[status] = OrderStatus.Created
        }
    }
}

object CustomersTable : IntIdTable() {
    val firstName = varchar("first_name", 20)
    val lastName = varchar("last_name", 20)
    val email = varchar("email", 50).nullable().uniqueIndex()
}

private object OrdersTable : IntIdTable() {
    val totalDue = varchar("total_due", 10)
    val customerId = integer("customer_id").references(CustomersTable.id, onDelete = ReferenceOption.CASCADE)
        .index()

    val status = enumerationByName<OrderStatus>("status", 20)
}

enum class OrderStatus {
    Created,
    Due,
    PastDue,
    Cancelled,
    Paid,
    Returned,
}


fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db_populated",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

