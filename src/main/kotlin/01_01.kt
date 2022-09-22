import org.jetbrains.exposed.sql.Database

fun main() {
    println("Hello Exposed!")
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}