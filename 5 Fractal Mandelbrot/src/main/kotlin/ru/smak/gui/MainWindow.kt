package ru.smak.gui

import ru.smak.gui.components.GraphicsPanel
import ru.smak.gui.graphics.*
import ru.smak.gui.graphics.convertation.CartesianScreenPlane
import ru.smak.gui.graphics.convertation.Converter
import ru.smak.math.fractals.Mandelbrot
import java.awt.Color
import java.awt.Dimension
import java.awt.event.*
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JColorChooser
import javax.swing.JFrame

class MainWindow : JFrame(){

    var currentStep : Int = 0 ///
    var historySteps: MutableMap<Int, List<Double>> = mutableMapOf()  /// MutableMap<Key, Value>

    val ButtonBack = JButton("Назад")  ///
    val ButtonReset = JButton("Сбросить")   ///
    private val minSize = Dimension(300, 200)
    private val mainPanel: GraphicsPanel
    lateinit var sb1: StoringSteps
    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Построение множества Мандельброта"
        minimumSize = Dimension(700, 700)
        mainPanel = GraphicsPanel()

        layout = GroupLayout(contentPane).apply{  // apply возвращает объект
            setVerticalGroup(createSequentialGroup()
                    .addGap(4)
                    .addComponent(mainPanel, minSize.height, minSize.height, GroupLayout.DEFAULT_SIZE)
                    .addComponent(ButtonBack,20, 20, 20) ///
                    .addComponent(ButtonReset,20, 20, 20) ///
                    .addGap(4)
            )
            setHorizontalGroup(createSequentialGroup()
                    .addGap(4)
                    .addGroup(
                            createParallelGroup()
                                    .addComponent(mainPanel, minSize.width, minSize.width, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
                    .addGroup(  ///
                            createParallelGroup()
                                    .addComponent(ButtonBack,20, 100, 100)  // ширина x
                                    .addComponent(ButtonReset,20, 100, 100)  // ширина x
                    )
                    .addGap(4) ///
            )
        }

        pack()

        val plane = CartesianScreenPlane(
            mainPanel.width, mainPanel.height,
            -2.0, 1.0, -1.0, 1.0
        )

        val mfp = SelectionFramePainter(mainPanel.graphics)
        val fractal = Mandelbrot()  // создаём мандельброта
        val fp = FractalPainter(plane)  // создаём пеинтер
        fp.fractalTest = fractal::isInSet  // у фракталпеинтера фракталтест
        fp.getColor = ::colorScheme4

        with (mainPanel){
            background = Color.WHITE
            addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    plane.realWidth = mainPanel.width
                    plane.realHeight = mainPanel.height
                    mfp.g = mainPanel.graphics
                }
            })
            addMouseListener(object: MouseAdapter(){
                override fun mousePressed(e: MouseEvent?) {
                    e?.let {
                        mfp.isVisible = true
                        mfp.startPoint = it.point
                    }
                }

                override fun mouseReleased(e: MouseEvent?) {  // mouseReleased отвечает за отпускание кнопки мыши
                    e?.let{
                        mfp.currentPoint = it.point
                    }
                    mfp.isVisible = false
                    mfp.selectionRect?.apply {
                        val xMin = Converter.xScr2Crt(x, plane)
                        val xMax = Converter.xScr2Crt(x + width, plane)
                        val yMin = Converter.yScr2Crt(y + height, plane)
                        val yMax = Converter.yScr2Crt(y, plane)

                        currentStep += 1
                        historySteps.put(currentStep, listOf(xMin, xMax, yMin, yMax))

                        for ((key, value) in historySteps) {
                            println(key)
                            println(value)
                        }
                        println("----------------")

                        plane.also{
                            it.xMin = historySteps.values.last()[0]
                            it.xMax = historySteps.values.last()[1]
                            it.yMin = historySteps.values.last()[2]
                            it.yMax = historySteps.values.last()[3]
                        }
                    }
                    repaint()
                }
            })
            addMouseMotionListener(object : MouseMotionAdapter(){
                override fun mouseDragged(e: MouseEvent?) {
                    e?.let{
                        mfp.currentPoint = it.point
                    }
                }
            })

            ButtonBack.addActionListener {  /// Назад
                println("Назад")
                // а по нажатии на кнопку "назад" удалять последние данные из historySteps
//                plane.also{  // Новая отрисовка
//                    it.xMin = sb1.xMin
//                    it.xMax = sb1.xMax
//                    it.yMin = sb1.yMin
//                    it.yMax = sb1.yMax
//                }
                repaint()
            } 
            ButtonReset.addActionListener {  /// Сброс
                println("Сброс")
                plane.also{  // Новая отрисовка
                    it.xMin = -2.0
                    it.xMax = 1.0
                    it.yMin = -1.0
                    it.yMax = 1.0
                }
                repaint()
            }

            addPainter(fp)  // вызываем
        }
    }
}