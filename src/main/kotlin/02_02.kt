import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    connect()

    transaction {
        val capitalized = CustomersTable.firstName.function("UPPE").alias("capitalized")
        CustomersTable
            .slice(
                CustomersTable.id,
                capitalized
            )
            .selectAll()
            .forEach { row ->
                println(row[capitalized])
            }
    }
}

object CustomersTable : IntIdTable() {
    val firstName = varchar("first_name", 20)
    val lastName = varchar("last_name", 20)
    val email = varchar("email", 50).nullable()
}

fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db_populated",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

