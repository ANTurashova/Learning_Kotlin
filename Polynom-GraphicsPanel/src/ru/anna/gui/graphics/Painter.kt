// Здесь описываем какие-то методы, которые мне будут нужны
package ru.anna.gui.graphics

import java.awt.Graphics

interface Painter {
    fun paint(g: Graphics?)  // В GraphicsPanel вызываем этот метод. Включаем здесь метод в интерфейс.
}