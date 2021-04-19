fun main() {
//    DBHelper("students")
//    val db = DBController("test")
    val dbHelper = DBHelper("students")
    dbHelper.apply {
        dropAllTables()
        createDataBaseFromDump("students.sql")
        fillTableFromCSV("department", "data/department.csv")
        fillTableFromCSV("discipline", "data/discipline.csv")
        fillTableFromCSV("specialization", "data/specialization.csv")
        fillTableFromCSV("curriculum", "data/curriculum.csv")
        fillTableFromCSV("group", "data/group.csv")
        fillTableFromCSV("curriculum_discipline", "data/curriculum_discipline.csv")
        fillTableFromCSV("student", "data/student.csv")
        fillTableFromCSV("academic_performance", "data/academic_performance.csv")
    }
}