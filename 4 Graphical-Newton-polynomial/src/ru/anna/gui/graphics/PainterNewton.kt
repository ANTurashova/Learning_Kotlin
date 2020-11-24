package ru.anna.gui.graphics

import ru.anna.gui.graphics.convertation.CartesianScreenPlane
import ru.anna.gui.graphics.convertation.Converter
import ru.anna.polynoms.Newton
import java.awt.*

class PainterNewton(val plane: CartesianScreenPlane, val newton: Newton) : Painter
{
    private var color = Color.BLACK
    fun setColor(newColor: Color){color = newColor}

    override fun  paint(g: Graphics?)
    {
        if (g==null) return
        val g2 = g as Graphics2D
        g2.stroke = BasicStroke(1.5f)  // f нужна, потому что метод принимает значения типа float, без него будет double
        val rh = mapOf(  // карта настроек сглаживания рисования
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
        )
        g2.setRenderingHints(rh)

        drawPoints(g2)
        drawLines(g2)
    }

    private fun drawPoints(g:Graphics?){
        if(g != null){
            g.color= Color.BLUE  // Точечки
            val arrayPoints = mutableMapOf<Double,Double>()
            for(i in 0 until newton.xfx.size){
                arrayPoints[newton.xfx[i].first] = newton.divDiff[Pair(i, i)]!!
            }
            for(i in 0 until arrayPoints.size){
                g.fillOval(
                        Converter.xCrt2Scr(arrayPoints.keys.elementAt(i),plane) - 4,
                        Converter.yCrt2Scr(arrayPoints.values.elementAt(i),plane) - 4,
                        8,
                        8
                )
            }
        }
    }

    private  fun drawLines(g:Graphics?){
        if(g != null){
            g.color=color
            val n = ((plane.xMax - plane.xMin) * 100).toInt()  // Кол-во разбиений
            val pointsArray = mutableMapOf<Double,Double>()                                                              // Точек на 1 больше чем разбиений
            if(newton.xfx.size != 0){
                for(i in 0..n){  // Добавляем точечки
                    pointsArray[plane.xMin + ((plane.xMax-plane.xMin)*i)/n] = newton.invoke(plane.xMin + ((plane.xMax-plane.xMin)*i)/n)
                }
                for(i in 0..pointsArray.size - 2){
                    g.drawLine(  // Рисуем отрезочки между точками
                            Converter.xCrt2Scr(pointsArray.keys.elementAt(i),plane),                                     // x1
                            Converter.yCrt2Scr(pointsArray.values.elementAt(i),plane),                                   // y1
                            Converter.xCrt2Scr(pointsArray.keys.elementAt(i+1),plane),                             // x2
                            Converter.yCrt2Scr(pointsArray.values.elementAt(i+1),plane)                            // y2
                    )
                }
            }
        }
    }
}