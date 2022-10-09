import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CustomersTableTest {
    @BeforeEach
    fun setup() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/sports_db_test",
            user = "sports_db_admin",
            password = "abcd1234"
        )
        transaction {
            SchemaUtils.create(CustomersTable)
        }
    }


    @AfterEach
    fun teardown() {
        transaction {
            SchemaUtils.drop(CustomersTable)
        }
    }

    @Test
    fun `insert creates a new row in Customers`() = transaction {
        val newId = CustomersTable.insertAndGetId { row ->
            row[firstName] = "Nicholas"
            row[lastName] = "Clark"
            row[city] = "Danbury"
            row[state] = "CT"
        }
        val row = CustomersTable.select { CustomersTable.id eq newId }.firstOrNull()

        assertNotNull(row)
        assertEquals("Nicholas", row!![CustomersTable.firstName])
    }
}