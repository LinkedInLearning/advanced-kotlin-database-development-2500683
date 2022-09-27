import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    connect()

    transaction {

    }
}

fun recreateTables() = transaction {
    SchemaUtils.drop(CustomersTable)
    SchemaUtils.create(CustomersTable)
}

fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

object CustomersTable : IntIdTable() {
    val name = varchar("name", 20)
    val email = varchar("email", 50).nullable()
}