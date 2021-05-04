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
//    init {
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:mysql://$address:$port/$dbName?serverTimezone=UTC",
//                    user,
//                    password
//            )
//            // Демонстрация
//            val s = connection?.createStatement()
//            val sql_create = "create table `3test2` (\n" +
//                    "    `id` int primary key auto_increment,\n" +
//                    "    `text_field` varchar(50) not null,\n" +
//                    "    `int_field` int default 0\n" +
//                    ")"
//            s?.execute(sql_create) // Исполняем запрос
//        }catch (e: SQLException){
//            println("Ошибка создания таблицы:\n${e.toString()} ")
//        }
//    }

    init {
        connection = DriverManager.getConnection(
                "jdbc:mysql://$address:$port/$dbName?serverTimezone=UTC", user, password
        )

    }

    fun dropAllTables(){
        println("Удаление всех таблиц в базе данных...")
        val s = connection?.createStatement()
        s?.execute("DROP TABLE if exists `academic_performance`")
        s?.execute("DROP TABLE if exists `student`")
        s?.execute("DROP TABLE if exists `group`")
        s?.execute("DROP TABLE if exists `curriculum_discipline`")
        s?.execute("DROP TABLE if exists `curriculum`")
        s?.execute("DROP TABLE if exists `discipline`")
        s?.execute("DROP TABLE if exists `department`")
        s?.execute("DROP TABLE if exists `specialization`")
        println("Все таблицы удалены.")

    }

    /**Функция, создающая таблицы в базе данных на основе SQL-дампа
     * @param path путь до SQL-дампа*/
    fun createDataBaseFromDump(path: String){
        println("Создание структуры базы данных из дампа...")
        try {
            val s = connection?.createStatement()
            var query = ""
            File(path).forEachLine {
                if(!it.startsWith("--") && it.isNotEmpty()){
                    query += it;
                    if (it.endsWith(';')) {
                        s?.addBatch(query)
                        query = ""
                    }
                }
            }
            s?.executeBatch()
            println("Структура базы данных успешно создана.")
        }catch (e: SQLException){ println(e.message)
        }catch (e: Exception){ println(e.message)}
    }

    /**Функция для заполнения таблицы из CSV - файла
     * @param table название таблицы в базе данных
     * @param path путь до источника данных (CSV - файла)
     * TODO Добавить Exception для ощибок с чтением файла*/
    fun fillTableFromCSV(table: String, path: String){

        println("Заполнение таблицы $table из файла $path")
        val s = connection?.createStatement()
        try {
            var requestTemplate = "INSERT INTO `${table}` "
            val dataBufferedReader = File(path).bufferedReader()
            val columns = dataBufferedReader.readLine()
                    .split(',')
                    .toString()
            requestTemplate += "(${columns.substring(1, columns.length - 1)}) VALUES "

            while (dataBufferedReader.ready()){
                var request = "$requestTemplate("
                val data = dataBufferedReader.readLine().split(',')
                data.forEachIndexed{i, column ->
                    request += "\"$column\""
                    if (i < data.size - 1) request += ','
                }
                request += ')'
                s?.addBatch(request)
            }
            s?.executeBatch()
            s?.clearBatch()

        }catch (e: SQLException){ println(e)}

    }





    fun requestScholarship(){
        println("Стипендии за последнюю сессию")
        val s = connection?.createStatement()
        val rs = s?.executeQuery(
                "SELECT id_student, last_name, first_name, patronymic, id_group, scholarship FROM student JOIN \n" +
                        "(SELECT id_student_a_p, \n" +
                        "    (CASE WHEN MIN(result) = 5 THEN 3100 \n" +
                        "        WHEN MIN(result) = 4 THEN 2100\n" +
                        "        ELSE 0\n" +
                        "    \tEND) AS scholarship\n" +
                        "                        \n" +
                        "\tFROM \n" +
                        "\t(SELECT id_student_a_p, score, attempt, reporting_form,\n" +
                        "    \t(CASE WHEN reporting_form = \"экзамен\" AND score > 85 AND attempt = 1 THEN 5 \n" +
                        "        \tWHEN reporting_form = \"экзамен\" AND score > 70 AND attempt = 1 THEN 4\n" +
                        "          \tWHEN reporting_form = \"экзамен\" AND score > 55 AND attempt = 1 THEN 3\n" +
                        "          \tWHEN reporting_form = \"зачет\" AND score > 55 AND attempt = 1 THEN 5\n" +
                        "          \tELSE 0\n" +
                        "    \t\tEND) AS result \n" +
                        "\n" +
                        "\t\tFROM academic_performance JOIN curriculum_discipline JOIN \n" +
                        "\t\t(SELECT student.id_student, current_semester FROM student JOIN\n" +
                        "    \t\t(SELECT year, group.id_group, 2*(YEAR(NOW()) - year) - \n" +
                        "       \t\t\t(CASE WHEN MONTH(NOW()) > 6 OR MONTH(NOW()) = 1 THEN 1 ELSE 0 \n" +
                        "                 \tEND) \n" +
                        "             \tAS current_semester\n" +
                        "    \t\n" +
                        "             \tFROM `group` JOIN curriculum ON group.id_curriculum = curriculum.id_curriculum) \n" +
                        "\t\t\tAS group2\n" +
                        "        \tON student.id_group = group2.id_group) \n" +
                        "     \tAS swcs ON academic_performance.id_student_a_p = swcs.id_student\n" +
                        "     \tAND curriculum_discipline.id_c_d = academic_performance.id_c_d \n" +
                        "     \tAND swcs.current_semester = curriculum_discipline.semester_number + 1) \n" +
                        "\tAS `rank` GROUP BY id_student_a_p) \n" +
                        "AS swsh\n" +
                        "ON student.id_student = swsh.id_student_a_p  \n" +
                        "ORDER BY student.id_student ASC")



        while (rs?.next() == true){
            println("${rs.getInt("id_student")}\t" +
                    "${rs.getString("last_name")}\t" +
                    "${rs.getString("first_name")}\t" +
                    "${rs.getString("patronymic")}\t" +
                    "${rs.getString("id_group")}\t" +
                    "${rs.getInt("scholarship")}\t"
            )

        }


    }
}


