import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    connect()

    transaction {
        SchemaUtils.createMissingTablesAndColumns(CustomersTable)

        // This will throw an exception, since the email was already used by another customer
        CustomersTable.insert { row ->
            row[firstName] = "Jane"
            row[lastName] = "Carter"
            row[email] = "mcooper4@go.com"
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
}

fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db_populated",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

