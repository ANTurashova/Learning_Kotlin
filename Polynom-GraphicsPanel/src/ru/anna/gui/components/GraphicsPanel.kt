package ru.anna.gui.components

import javax.swing.JPanel
import ru.anna.gui.graphics.Painter
import java.awt.Graphics


/* class GraphicsPanel(private val painter: Painter) : JPanel() {  // JPanel является универсальным контейнером. GraphicsPanel наследует от JPanel. painter: Painter ссылает на класс Painter
    // Нам нужен метод, который обновлял бы содержание это панельки для каждой перерисовки.
    override fun paint(g: Graphics?) {  // Переопределим метод paint.
        super.paint(g)  // Первый вызов в методе - это отрисовка панельки супер классом.
        painter.paint(g)  // Мы очевидно обратимся к painter, вызовем в нём метод paint и передам туда g
///        this.graphics  это можно использовать вне метода paint
    }
 } */

class GraphicsPanel : JPanel(){  // когда мы создаём панельку, нам не нужно ничего в неё передавать, она пустая создаётся
    var painter: Painter? = null  // painter сам по себе будет наллыбл, по умолчанию будет отсутствовать
    set(value) {
        field = value  // field - теневое свойство, мы обращаемся к полю, которое стоит за этим свойством.
        repaint()  // При смене painter-а нужно панельку перерисовать, для этого вызываем метод repaint()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        painter?.paint(g)
    }

}