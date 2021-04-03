// Класс для взаимодействия с базами данных
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBHelper(
    private val dbName: String,
    private val address: String = "localhost",
    private val port: Int = 3306,
    private val user: String = "root",
    private val password: String = "root"
){
    private var connection: Connection? = null
    init {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://$address:$port/$dbName?serverTimezone=UTC",
                    user,
                    password
            )
            // Демонстрация
            val s = connection?.createStatement()
            val sql_create = "create table `3test2` (\n" +
                    "    `id` int primary key auto_increment,\n" +
                    "    `text_field` varchar(50) not null,\n" +
                    "    `int_field` int default 0\n" +
                    ")"
            s?.execute(sql_create) // Исполняем запрос
        }catch (e: SQLException){
            println("Ошибка создания таблицы:\n${e.toString()} ")
        }

    }

}


