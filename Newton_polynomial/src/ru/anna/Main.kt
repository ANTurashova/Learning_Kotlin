package ru.anna

fun main(){
//    val window = Window()
//    window.isVisible = true
    //window.isResizable = false
    val p = Newton(arrayListOf(Pair(-1.0, -1.0)))
    println(p)
    p.newPoint(2.0, 8.0)
    println(p)
    p.newPoint(1.0, 1.0)
    println(p)
    p.newPoint(0.0, 0.0)
    println(p)
    p.newPoint(0.0, 0.0)
    println(p)
    p.newPoint(10.0, 1000.0)
    println(p)
}

//fun main(){
//    val p1 = Polynom(doubleArrayOf(1.0, 0.0, 3.0, 0.0, 0.0))
//
//    val n3 = Newton(mutableMapOf(
//        1.0 to 4.0,
//        2.0 to 9.0
//    ))
//    println(n3)
//}