import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    connect()

    transaction {
        exec("GRANT SELECT ON Customers to public")
        val results = exec("select id, first_name from customers where id = ?",
            listOf(IntegerColumnType() to 1000)
        ) { resultSet ->
            val results = mutableListOf<Int>()
            while (resultSet.next()) {
                results.add(resultSet.getInt(1))
            }
            results
        }

        println(results)
    }
}

object CustomersTable : IntIdTable() {
    val firstName = varchar("first_name", 20)
    val lastName = varchar("last_name", 20)
    val email = varchar("email", 50).nullable().uniqueIndex()
    val city = varchar("city", 20)
    val state = varchar("state", 20)
}

private object OrdersTable : IntIdTable() {
    val totalDue = varchar("total_due", 10)
    val customerId = integer("customer_id")
        .references(CustomersTable.id, onDelete = ReferenceOption.CASCADE)
        .index()

    val status = customEnumeration(
        "status",
        "varchar(20)",
        OrderStatus::fromDb,
        OrderStatus::toDb
    )
}

enum class OrderStatus {
    Created,
    Due,
    PastDue,
    Cancelled,
    Paid,
    Returned;

    companion object {
        fun toDb(orderStatus: OrderStatus): String {
            return when (orderStatus) {
                Created -> "created"
                Due -> "due"
                PastDue -> "past due"
                Cancelled -> "cancelled"
                Paid -> "paid"
                Returned -> "returned"
            }
        }

        fun fromDb(dbValue: Any): OrderStatus {
            return when (dbValue) {
                "created" -> Created
                "due" -> Due
                "past due" -> PastDue
                "cancelled" -> Cancelled
                "paid" -> Paid
                "returned" -> Returned
                else -> throw RuntimeException("Unknown status: $dbValue")
            }
        }
    }
}


fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db_populated",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

