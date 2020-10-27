// Этот класс будет реализовывать интерфейс Painter
package ru.anna.gui.graphics  // Декартова

import ru.anna.gui.graphics.convertation.CartesianScreenPlane
import ru.anna.gui.graphics.convertation.Converter
import java.awt.Color
import java.awt.Graphics

class CartesianPainter(val plane: CartesianScreenPlane) : Painter{ // Так как класс реализует интерфейс, то после двоеточия пишется имя интерфейса. Если класс расширяет какой-то класс, то после имени пишут ().

// Окружность:
//    override fun paint(g: Graphics?) {  // Напишем, что будем выводить на экран.
//        if (g!=null) {
//            g.color = Color.BLUE  // цвет графики
//            g.fillOval(10, 10, 300, 300)  // овал
//        }
//    }

    override fun paint(g: Graphics?) {  // Напишем, что будем выводить на экран.
        drawAxes(g)  // Axes = оси
    }

    private fun drawAxes(g: Graphics?) {  // рисуем оси
        if (g!=null) {  // так надо
            g.color = Color.BLACK
            g.drawLine(0,  // Горизонт ось
                    Converter.yCrt2Scr(0.0,plane),
                    plane.width,
                    Converter.yCrt2Scr(0.0, plane)
            )
            g.drawLine(  // Вертик ось
                    Converter.xCrt2Scr(0.0, plane),
                    0,
                    Converter.xCrt2Scr(0.0, plane),
                    plane.height
            )
        }

    }

}