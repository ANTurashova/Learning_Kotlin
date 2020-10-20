// Этот класс будет реализовывать интерфейс Painter
package ru.anna.gui.graphics  // Декартова

import java.awt.Color
import java.awt.Graphics

class CartesianPanter : Painter { // Так как класс реализует интерфейс, то после двоеточия пишется имя интерфейса. Если класс расширяет какой-то класс, то после имени пишут ().

    override fun paint(g: Graphics?) {  // Напишем, что будем выводить на экран.
        if (g!=null) {
            g.color = Color.BLUE  // цвет графики
            g.fillOval(10, 10, 300, 300)  // овал
        }
    }
}