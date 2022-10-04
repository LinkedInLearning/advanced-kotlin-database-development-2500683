import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

fun main() {
    connect()

    transaction {
        SchemaUtils.createMissingTablesAndColumns(OrdersTable)

        CustomersTable.deleteWhere { CustomersTable.id eq 100 }
    }
}

object CustomersTable : IntIdTable() {
    val firstName = varchar("first_name", 20)
    val lastName = varchar("last_name", 20)
    val email = varchar("email", 50).nullable()
}

private object OrdersTable : IntIdTable() {
    val totalDue = varchar("total_due", 10)
    val customerId = integer("customer_id").references(CustomersTable.id, onDelete = ReferenceOption.CASCADE)
}

fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db_populated",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

