package ru.anna.gui.graphics  // Декартова

import java.awt.Color
import java.awt.Graphics

class CartesianPanter : Painter{
    override fun paint(g: Graphics?) {
        if (g!=null) {
            g.color = Color.BLUE
            g.fillOval(10, 10, 300, 300)
        }
    }

}