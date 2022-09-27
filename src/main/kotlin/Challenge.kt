import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

fun main() {
    connect()

    transaction {
        recreateTables()

        OrdersTable.insert { row ->
            row[totalDue] = BigDecimal(140.91)
            row[status] = "Paid"
        }

        OrdersTable.insert { row ->
            row[totalDue] = BigDecimal(105.32)
            row[status] = "Returned"
        }

        OrdersTable.insert { row ->
            row[totalDue] = BigDecimal(217.30)
            row[status] = "Past due"
        }

        OrdersTable.insert { row ->
            row[totalDue] = BigDecimal(281.39)
            row[status] = "Paid"
        }

        OrdersTable.update({ OrdersTable.status eq "Returned" }) {row ->
            row[totalDue] = BigDecimal(246.12)
        }

        OrdersTable.deleteWhere { OrdersTable.totalDue less 200 }

        OrdersTable.select { (OrdersTable.status eq "Paid") and (OrdersTable.totalDue greater 220) }.forEach {
            println(it)
        }
    }
}

fun recreateTables() = transaction {
    SchemaUtils.drop(CustomersTable, OrdersTable)
    SchemaUtils.create(CustomersTable, OrdersTable)
}

fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

object OrdersTable : LongIdTable() {
    val totalDue = decimal("total_due", 5, 2)
    val status = varchar("status", 20)
}

object CustomersTable : IntIdTable() {
    val name = varchar("name", 20)
    val email = varchar("email", 50).nullable()
}