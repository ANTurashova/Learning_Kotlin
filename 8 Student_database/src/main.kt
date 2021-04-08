fun main() {
//    DBHelper("students")
//    val db = DBController("test")
    val dbHelper = DBHelper("students")
    dbHelper.apply {
        dropAllTables()
        createDataBaseFromDump("students.sql")
        fillTableFromCSV("cathedras", "data/cathedras.csv")
        fillTableFromCSV("disciplines", "data/disciplines.csv")
        fillTableFromCSV("specializations", "data/specializations.csv")
        fillTableFromCSV("academic_plans", "data/academic_plans.csv")
        fillTableFromCSV("groups", "data/groups.csv")
        fillTableFromCSV("disciplines_plans", "data/disciplines_plans.csv")
        fillTableFromCSV("students", "data/students.csv")
        fillTableFromCSV("performance", "data/performance.csv")
    }
}