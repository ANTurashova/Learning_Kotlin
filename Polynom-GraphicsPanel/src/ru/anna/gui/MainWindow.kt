package ru.anna.gui

import ru.anna.gui.components.GraphicsPanel
import ru.anna.gui.graphics.CartesianPainter
import ru.anna.gui.components.ControlPanel
import ru.anna.gui.graphics.convertation.CartesianScreenPlane
import java.awt.Color
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JFrame
import ru.anna.gui.graphics.PainterNewton
import ru.anna.gui.graphics.convertation.Converter
import ru.anna.polynoms.Newton
import java.awt.event.*

class MainWindow : JFrame(){

    private val minSize = Dimension(450, 400)
    private val mainPanel: GraphicsPanel
    private val controlPanel: ControlPanel
    private val newton = Newton(ArrayList())

    init{
        defaultCloseOperation = EXIT_ON_CLOSE  // Чтобы по нажатию на крестик закрылась программа
        minimumSize = Dimension(minSize.width+180, minSize.height+315)
        mainPanel = GraphicsPanel()  /// устар: когда мы создаём графикс панель, мы должны указать объект класса Painter
        mainPanel.background = Color.WHITE
        controlPanel = ControlPanel()
        val gl = GroupLayout(contentPane)

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, minSize.height, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(4)
        )

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(mainPanel, minSize.width, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4))
        layout = gl  // отрисовка всей верхней части с рисуночками
        pack()

        val plane = CartesianScreenPlane(  // создаём плоскость
            mainPanel.width, mainPanel.height,
            controlPanel.smXMin.number.toDouble(),  // обратимся к controlPanel и возьмём значение из скролла
            controlPanel.smXMax.number.toDouble(),
            controlPanel.smYMin.number.toDouble(),
            controlPanel.smYMax.number.toDouble()
        )

        val dp = CartesianPainter(plane)  // plane - плоскоскость, которую передаём // Создадим объект класса CartesianPanter

        val np = PainterNewton(plane,newton)

        mainPanel.addComponentListener(object : ComponentAdapter() {  // Чтобы обновлялась отрисовка у графика
            override fun componentResized(e: ComponentEvent?) {  // Что делать, если меняется размер панели
                dp.plane.realWidth = mainPanel.width
                dp.plane.realHeight = mainPanel.height
                np.plane.realWidth = mainPanel.width
                np.plane.realHeight = mainPanel.height
            }
        })

        controlPanel.addValChangeListener{  // Подпишимся на событие изменения значения этих спинеров
            dp.plane.xMin = controlPanel.smXMin.number.toDouble()
            dp.plane.xMax = controlPanel.smXMax.number.toDouble()
            dp.plane.yMin = controlPanel.smYMin.number.toDouble()
            dp.plane.yMax = controlPanel.smYMax.number.toDouble()
            np.plane.xMin = controlPanel.smXMin.number.toDouble()
            np.plane.xMax = controlPanel.smXMax.number.toDouble()
            np.plane.yMin = controlPanel.smYMin.number.toDouble()
            np.plane.yMin = controlPanel.smYMin.number.toDouble()
            mainPanel.repaint()
        }

        mainPanel.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                if(e!=null){  // Проверяем клик по панели mainPanel
                    if(e.point.x < plane.realWidth && e.point.x > 0 && e.point.y > 0 && e.point.y < plane.realHeight)   // Добавляем в полином точки
                    {
                        newton.newPoint(Converter.xScr2Crt(e.point.x,plane),Converter.yScr2Crt(e.point.y,plane))
                        mainPanel.repaint()
                    }
                }
            }
        })

        mainPanel.addPainter(dp)
        mainPanel.addPainter(np)
    }
}