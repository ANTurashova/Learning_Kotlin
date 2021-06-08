import java.sql.Connection
import java.sql.DriverManager


class DBController(
    private val dbName: String,
    private val address: String = "localhost",
    private val port: Int = 3306,
    private val user: String = "root",
    private val password: String = "root"
) {
    private var connection: Connection? = null

    init {
        connection = DriverManager.getConnection(
            "jdbc:mysql://$address:$port/$dbName?serverTimezone=UTC", user, password
        )
    }

    fun addMessage(client_id: Int, message: String) {
        val s = connection?.createStatement()
        s?.execute(
            "INSERT INTO `messages` (`client_id`, `message`) " +
                    "VALUES ('$client_id', '$message');"
        )
    }
}