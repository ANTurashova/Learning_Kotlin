package ru.anna

import java.awt.Color
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants
import javax.swing.border.BevelBorder
import javax.swing.border.EtchedBorder

class MainWindow : JFrame(){
    private val minSize = Dimension(600, 550)  // размер окна при открытии
    private val mainPanel: JPanel  // панель
    private val controlPanel: JPanel

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE  //операция закрытия по умолчанию = зыкрывать при нажатии
        minimumSize = minSize  // минимальный размер окна
        size=minSize  // устанавливает размер открываемого окна
        mainPanel = JPanel()  // создать панель
        mainPanel.background = Color.WHITE  // белый цвет панели
        controlPanel = JPanel()
        controlPanel.border = EtchedBorder()
//        mainPanal.setSize(400,300)  // размер панели
//        mainPanal.setLocation(10,10)  // расположение левого верхнего угла панели
        val gl = GroupLayout(contentPane)  // менеджер раскладок // contentPane - часть окна без заголовка

        gl.setVerticalGroup(gl.createSequentialGroup()  // вертикаль
                .addGap(4)  // зазорчик
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel, 100, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(4)
        )

        gl.setHorizontalGroup(gl.createSequentialGroup()  // горизонталь
                .addGap(4)
                .addGroup(  // для параллельных компонентов
                        gl.createParallelGroup()
                                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4)
        )

        layout = gl  // размеры компонентов будут такие, какие описали
//        add(mainPanel)  // добавление панели  // не надо, если уже описали .setVerticalGroup
        pack()
    }
}