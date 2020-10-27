package ru.anna.gui

import ru.anna.gui.components.GraphicsPanel
import ru.anna.gui.graphics.CartesianPainter
import ru.anna.gui.components.ControlPanel
import ru.anna.gui.graphics.convertation.CartesianScreenPlane
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.GroupLayout
import javax.swing.JFrame

class MainWindow : JFrame(){

    private val minSize = Dimension(500, 300)
    private val mainPanel: GraphicsPanel
    private val controlPanel: ControlPanel
    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(minSize.width+200, minSize.height+400)
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

        mainPanel.addComponentListener(object : ComponentAdapter() {  // Чтобы обновлялась отрисовка у графика
            override fun componentResized(e: ComponentEvent?) {  // Что делать, если меняется размер панели
                dp.plane.realWidth = mainPanel.width
                dp.plane.realHeight = mainPanel.height
            }
        })

        controlPanel.addValChangeListener {  // подпишимся на событие изменения изначения этих спинеров
            dp.plane.xMin = controlPanel.smXMin.number.toDouble()
            dp.plane.xMax = controlPanel.smXMax.number.toDouble()
            dp.plane.yMin = controlPanel.smYMin.number.toDouble()
            dp.plane.yMax = controlPanel.smYMax.number.toDouble()
            mainPanel.repaint()
        }

        mainPanel.painter = dp
    }
}