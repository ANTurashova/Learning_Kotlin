// Этот класс будет реализовывать интерфейс Painter
package ru.anna.gui.graphics  // Декартова

import ru.anna.gui.graphics.convertation.CartesianScreenPlane
import ru.anna.gui.graphics.convertation.Converter
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import kotlin.math.round

class CartesianPainter(val plane: CartesianScreenPlane) : Painter{ // Так как класс реализует интерфейс, то после двоеточия пишется имя интерфейса. Если класс расширяет какой-то класс, то после имени пишут ().

// Окружность:
//    override fun paint(g: Graphics?) {  // Напишем, что будем выводить на экран.
//        if (g!=null) {
//            g.color = Color.BLUE  // цвет графики
//            g.fillOval(10, 10, 300, 300)  // овал
//        }
//    }

    private enum class SizeType(val value:Int) {
        Small(3), Medium(5),Large(8)
    }

    override fun paint(g: Graphics?) {  // Напишем, что будем выводить на экран.
        drawAxes(g)  // Axes = оси
        drawTicks(g)
    }


    private fun drawXLabel(g: Graphics?, pos: Int, value: Double) {  // цифры-подписи:
        if (g != null) {
            val fnt = Font("Cambria", Font.BOLD, 12)  // выводить текст, циферки подписи оси
            g.font = fnt  // устанавливаю этот шрифт в качестве шрифта для графикса
            val m = g.fontMetrics  // метрики шрифта для объекта графикс
            g.color = Color.BLUE
            val (z1, z2) = xZeroPos
            val dx = if (value==0.0) 8 else
                    -m.getStringBounds(value.toString(), g).width.toInt()/2  // отцентрируем подписанные циферки
            val dy = if (value==0.0) 17 else
                    m.getStringBounds(value.toString(), g).height.toInt() +  // m - это метрики, у них есть метод getStringBounds(получить рамки(границы) строки). Короче этот метод возвращает некоторую прямоугольную область, в которой будет выводить нада строчка (value.toString()) в графическом контексте (g). У полученного прямоугольника беру высоту height.toInt() - это будет высота строки.
                    SizeType.Large.value + 1 // Ну и добавляю величину стрижка плю смещение 1 дополнительно, чтобы не прилипало
            if (z1 != z2) {
                g.drawString(value.toString(), pos + dx, z1 + dy)
                g.drawString(value.toString(), pos + dx, z2 - dy)
            } else
                g.drawString(value.toString(), pos + dx, z1 + dy)
        }
    }

    private fun drawYLabel(g: Graphics?, pos: Int, value: Double) {  // цифры-подписи:
        if (g != null) {
            val fnt = Font("Cambria", Font.BOLD, 12)  // выводить текст, циферки подписи оси
            g.font = fnt  // устанавливаю этот шрифт в качестве шрифта для графикса
            val m = g.fontMetrics  // метрики шрифта для объекта графикс
            g.color = Color.BLUE
            val (z1, z2) = yZeroPos
            val dx = if (value==0.0) 8 else
                    2 + SizeType.Large.value  // отцентрируем подписанные циферки
            val dy = if (value==0.0) 17 else
                    m.getStringBounds(value.toString(), g).height.toInt()/3
            if (z1 != z2) {
                g.drawString(value.toString(), z1 + dx, pos + dy)
                g.drawString(value.toString(),
                        z2 - dx - m.getStringBounds(value.toString(), g).width.toInt(),
                        pos + dy)
            } else
                g.drawString(value.toString(), z1 + dx, pos + dy)
        }
    }

    private fun drawTicks(g: Graphics?){
        drawXTicks(g)
        drawYTicks(g)
    }

    private fun drawXTicks(g: Graphics?){
        val min = round(plane.xMin * 10).toInt()
        val max = round(plane.xMax * 10).toInt()
        for (cur in min..max)
        {
            if (cur % 10 == 0)
                drawXLabel(g, Converter.xCrt2Scr(cur / 10.0, plane), cur / 10.0)
            drawXTick(
                    g,
                    Converter.xCrt2Scr(cur / 10.0, plane),
                    when{
                        cur % 10 == 0 -> SizeType.Large
                        cur % 5 == 0 -> SizeType.Medium
                        else -> SizeType.Small
                    }
            )
        }
    }

    private val xZeroPos: List<Int>
    get(){
        val z = Converter.yCrt2Scr(0.0, plane)
        if (z < 0 || z >= plane.width) {
            return listOf(0, plane.width)
        } else return listOf(z, z)
    }

    private val yZeroPos: List<Int>
        get(){
            val z = Converter.xCrt2Scr(0.0, plane)
            if (z < 0 || z >= plane.height) {
                return listOf(0, plane.height)
            } else return listOf(z, z)
        }



    private fun drawXTick(g: Graphics?, pos: Int, size: SizeType) {
        if (g != null){
            g.color = when (size){
                SizeType.Small -> Color.BLACK
                SizeType.Medium -> Color.BLUE
                SizeType.Large -> Color.BLUE
            }
            val (z1, z2) = xZeroPos
            if (z1 != z2) {
                g.drawLine(pos, z1, pos, z1 + size.value)
                g.drawLine(pos, z2 - size.value, pos, z2)
            }
            else g.drawLine(pos, z1-size.value, pos, z1+size.value)
        }
    }

    private fun drawYTicks(g: Graphics?) {
        val min = round(plane.yMin * 10).toInt()
        val max = round(plane.yMax * 10).toInt()
        for (cur in min..max)
        {
            if (cur % 10 == 0)
                drawYLabel(g, Converter.yCrt2Scr(cur / 10.0, plane), cur / 10.0)
            drawYTick(
                    g,
                    Converter.yCrt2Scr(cur / 10.0, plane),
                    when{
                        cur % 10 == 0 -> SizeType.Large
                        cur % 5 == 0 -> SizeType.Medium
                        else -> SizeType.Small
                    }
            )
        }
    }


    private fun drawYTick(g: Graphics?, pos: Int, size: SizeType) {
        if (g != null){
            g.color = when (size){
                SizeType.Small -> Color.BLACK
                SizeType.Medium -> Color.BLUE
                SizeType.Large -> Color.BLUE
            }  // а дальше что хз
            val (z1, z2) = yZeroPos
            if (z1 != z2) {
                g.drawLine(z1, pos, z1+ size.value, pos )
                g.drawLine(z2- size.value, pos, z2, pos)
            }
            else g.drawLine(z1-size.value, pos, z1+size.value, pos)
        }
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