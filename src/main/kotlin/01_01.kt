import org.jetbrains.exposed.sql.Database

//this is a comment
fun main() {
    println("Hello Exposed!")
    Database.connect(
        "jdbc:postgresql://localhost:5432/sports_db",
        user = "sports_db_admin",
        password = "abcd1234"
    )
}