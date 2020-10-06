package ru.anna.gui

import ru.anna.gui.components.ControlPanel  // Контрол панель
import ru.anna.gui.components.GraphicsPanel  // Граф панель
import ru.anna.gui.graphics.CartesianPanter  // Декартова
import java.awt.Color
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JPanel

class MainWindow : JFrame(){

    private val minSize = Dimension(550, 400)
    private val mainPanel: GraphicsPanel  // Граф панель
    private val controlPanel: ControlPanel  // Контрол панель
    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(minSize.width+200, minSize.height+400)
        val dp = CartesianPanter()  // Декартова
        mainPanel = GraphicsPanel(dp)  // Граф панель
        mainPanel.background = Color.WHITE
        controlPanel = ControlPanel()  // Контрол панель
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