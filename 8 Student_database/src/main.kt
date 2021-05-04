fun main() {
//    DBHelper("students")
//    val db = DBController("test")
    val dbHelper = DBHelper("students")
    dbHelper.apply {
//        dropAllTables()
//        createDataBaseFromDump("students.sql")
//        fillTableFromCSV("department", "data/department.csv")
//        fillTableFromCSV("discipline", "data/discipline.csv")
//        fillTableFromCSV("specialization", "data/specialization.csv")
//        fillTableFromCSV("curriculum", "data/curriculum.csv")
//        fillTableFromCSV("group", "data/group.csv")
//        fillTableFromCSV("curriculum_discipline", "data/curriculum_discipline.csv")
//        fillTableFromCSV("student", "data/student.csv")
//        fillTableFromCSV("academic_performance", "data/academic_performance.csv")

        requestScholarship()
    }
}








//fun main() {
//    //val db = DBController("test")
//    val dbHelper = `2DBHelper`("db_test")
//    dbHelper.apply {
////        dropAllTables()
////        createDataBaseFromDump("2db/src/2students.sql")
////        fillTableFromCSV("cathedras", "2db/data/cathedras.csv")
////        fillTableFromCSV("disciplines", "2db/data/disciplines.csv")
////        fillTableFromCSV("specializations", "2db/data/specializations.csv")
////        fillTableFromCSV("academic_plans", "2db/data/academic_plans.csv")
////        fillTableFromCSV("groups", "2db/data/groups.csv")
////        fillTableFromCSV("disciplines_plans", "2db/data/disciplines_plans.csv")
////        fillTableFromCSV("students", "2db/data/students.csv")
////        fillTableFromCSV("performance", "2db/data/performance.csv")
//        printScholarship()
//    }
//}