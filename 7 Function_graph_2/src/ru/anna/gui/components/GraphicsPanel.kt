package ru.anna.gui.components

import javax.swing.JPanel
import ru.anna.gui.graphics.Painter
import java.awt.Graphics

class GraphicsPanel : JPanel(){  // когда мы создаём панельку, нам не нужно ничего в неё передавать, она пустая создаётся
    var painters = mutableListOf<Painter>()
        set(value){
            field = value  // field - теневое свойство, мы обращаемся к полю, которое стоит за этим свойством.
            repaint()  // При смене painter-а нужно панельку перерисовать, для этого вызываем метод repaint()
        }

    override fun paint(g: Graphics?) {
        super.paint(g)  // Графику отрисовываем
        painters.forEach{
            it.paint(g)
        }
    }

    fun addPainter(painter: Painter){
        painters.add(painter)
        repaint()
    }
}