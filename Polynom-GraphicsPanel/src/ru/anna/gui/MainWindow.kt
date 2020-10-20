package ru.anna.gui

import ru.anna.gui.components.GraphicsPanel
import ru.anna.gui.graphics.CartesianPanter
import ru.anna.gui.components.ControlPanel
import java.awt.Color
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JPanel

class MainWindow : JFrame(){

    private val minSize = Dimension(550, 400)
///    private val mainPanel: JPanel  Сделаем основную панель типа GraphicsPanel
    private val mainPanel: GraphicsPanel
    private val controlPanel: ControlPanel
    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(minSize.width+200, minSize.height+400)
        val dp = CartesianPanter()  // Создадим объект класса CartesianPanter
///        mainPanel = JPanel()  Сделаем основную панель типа GraphicsPanel
        mainPanel = GraphicsPanel(dp)  // когда мы создаём графикс панель, мы должны указать объект класса Painter
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
        layout = gl
        pack()
    }
}