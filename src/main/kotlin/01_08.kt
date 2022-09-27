import CustomersTable.email
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    println("Hello Exposed!")
    connect()

    transaction {
        recreateTables()
        createCustomers()

        CustomersTable.update({ CustomersTable.id eq 2}){ row ->
            row[email] = "ecarr1@oracle.com"
        }

        CustomersTable.update({ CustomersTable.name eq "Carol"}){ row ->
            with(SqlExpressionBuilder) {
                row[name] = concat(name, name)
            }
        }
    }
}

fun createCustomers() = transaction {
    CustomersTable.insert { row ->
        row[name] = "Carol"
    }

    val newId = CustomersTable.insertAndGetId { row ->
        row[name] = "Elisabeth"
    }

    val newRow = CustomersTable.insert { row ->
        row[name] = "Ernest"
    }.resultedValues?.first()
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