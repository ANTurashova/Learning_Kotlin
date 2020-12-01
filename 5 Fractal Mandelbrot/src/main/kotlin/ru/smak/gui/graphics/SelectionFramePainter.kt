package ru.smak.gui.graphics
// рисовальщик рамочки

import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle

class SelectionFramePainter(var g: Graphics?){

    // Костыль, который решает баг с застревающим первым прямоугольником:
    init{
        g?.apply{
            setXORMode(Color.WHITE)
            drawRect(-2, -2, 1, 1)
            setPaintMode()
        }
    }

    var startPoint: Point? = null  // стартовая позиция
        set(value) {
            field = value
        }

    var currentPoint: Point? = null  // текущая позиция
        set(value) {
            if (field != null) paint()
            field = value
            paint()
        }

    var isVisible = false  //
        set(value) {
            if (!value)
                paint()
            else {
                currentPoint = null
                startPoint = null
            }
            field = value
        }

    // если cp выше и левее, этот класс сам всё вычисляет, получает прямоугольник:
    val selectionRect: Rectangle?  // Ещё одно свойство selectionRect. Тип у него Rectangle - стандартный класс прямоугольника
        // это св-во вычисляемое (значение в себе не хранит),  вычисляется на базе двух других св-в - стартовая точка и и текущая точка
        get() {
            startPoint?.let {sp->  // let для того, чтобы проверить, что startPoint не нулевой и currentPoint не нулевой
                currentPoint?.let {cp->
                    val r = Rectangle(sp)  // создаю прямоугольник с параметром startPoint, размер нулевой
                    r.add(cp)  // методом add добавляю текущую точку
                    return r  // возвращаю прямоугольник
                }
            }
            return null
        }

    private fun paint(){  // рисование самого прямоугольника
        if (isVisible){
            selectionRect?.let { r ->  // если selection не null, то:
            // let полезен при работе с объектами, которые могут принимать значение null. Вместо того, чтобы создавать длинные цепочки выражений if-else, можно просто скомбинировать оператор ? («оператор безопасного вызова») с let: в результате мы получим лямбду, у которой аргумент it является не nullable-версией исходного объекта
                g?.apply{  // проверим не null ли g, если и он не null, то:
                // .apply - оператор, возвращает объект, переданный в аргументе. позволяет выполнить несколько операций над одним объектом, не повторяя его имени
                    setXORMode(Color.WHITE)
                    color = Color.BLACK
                    drawRect(r.x, r.y, r.width, r.height)  // r это прямоугольник из selectionRect, ширина и высота внутри стандартного класса сами вычисляются
                    setPaintMode()
                }
            }
        }
    }
}