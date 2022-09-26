import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    println("Hello Exposed!")
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db",
        user = "sports_db_admin",
        password = "abcd1234"
    )


    transaction {
        SchemaUtils.drop(CustomersTable)
        SchemaUtils.create(CustomersTable)
    }
}

object CustomersTable : IntIdTable() {
    val name = varchar("name", 20)
    val email = varchar("email", 50).nullable()
}