package ru.anna

import ru.anna.gui.MainWindow
import ru.anna.polynoms.Lagrange
import ru.anna.polynoms.Polynom

fun main(){
//    val p1 = Polynom(doubleArrayOf(1.0, 0.0, 3.0, 0.0, 0.0))
//    val p2 = Polynom(doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0))
//    val p3 = Polynom(doubleArrayOf(0.0, 0.0, -3.0, 0.0, -1.0))
//    val p4 = Polynom(doubleArrayOf(7.0, 0.0, 3.0, 0.0, 0.0))
//    val p5 = Polynom(doubleArrayOf(-1.0, 0.0, 0.0, 0.0, 0.0))
//    val p6 = Polynom(doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0))
//    println(p1)
//    println(p2)
//    println(p3)
//    println(p4)
//    println(p5)
//    println(p6)
//    println(p1(2.0))
//    val l1 = Lagrange(mutableMapOf(
//            -1.0 to 1.0,
//            0.0 to 0.0,
//            1.0 to 1.0
//    ))
//    println(l1)

    val w = MainWindow()
    w.isVisible = true
}

// Расширяющий для GPanel верхей панели класс. Должен быть класс, который что-то рисует (просто рисовальщик).
// Нужна панель, которая может выводить нечто в себе - {}, что именно она в себе выводит - отдельный класс {}


// Если ось не видна, нумерацию выводить по краям
// Доп: если ось отдаляется, сделать более редкие штрихи