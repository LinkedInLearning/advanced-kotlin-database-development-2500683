import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.system.measureTimeMillis

fun main() {
    connect()

    transaction {
        SchemaUtils.createMissingTablesAndColumns(OrdersTable)

        createCustomersWithOrders(customers = 500_000, orderPerCustomer = 1)

        val timeTook = measureTimeMillis {
            val r = OrdersTable.select {
                OrdersTable.customerId eq (500_000 / 2)
            }.first()
            println(r[OrdersTable.customerId])
        }

        println("Time to execute: ${timeTook}ms")

        CustomersTable.deleteWhere { CustomersTable.id greater 1100 }
    }
}

fun createCustomersWithOrders(customers: Int, orderPerCustomer: Int) = transaction {
    var totalRows = 0L
    var percent = (customers * orderPerCustomer) / 100

    repeat(customers) {
        val n = it.toString()
        val newCustomerId = CustomersTable.insertAndGetId { row ->
            row[firstName] = n
            row[lastName] = n
        }
        repeat(orderPerCustomer) {
            OrdersTable.insert { row ->
                row[totalDue] = kotlin.random.Random.nextInt(0, 100).toString()
                row[customerId] = newCustomerId.value
            }
        }
        totalRows++
        if (totalRows % percent == 0L) {
            println("${it / percent}%")
            commit()
        }
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
        .index()
}

fun connect() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db_populated",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}

