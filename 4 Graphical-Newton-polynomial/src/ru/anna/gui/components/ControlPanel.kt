// Нижняя панель со скороллом координат, бывшая LagrangeControl
package ru.anna.gui.components

import java.awt.Color
import javax.swing.*
import javax.swing.border.EtchedBorder

class ControlPanel : JPanel(){

    private val lXMin: JLabel
    private val lXMax: JLabel
    private val lYMin: JLabel
    private val lYMax: JLabel

    private val sXMin: JSpinner  // скролл
    private val sXMax: JSpinner  // скролл
    private val sYMin: JSpinner  // скролл
    private val sYMax: JSpinner  // скролл

    val smXMin: SpinnerNumberModel  // скролл
    val smXMax: SpinnerNumberModel  // скролл
    val smYMin: SpinnerNumberModel  // скролл
    val smYMax: SpinnerNumberModel  // скролл

    val chButton1= JButton("Изменить цвет графика")
    val chButton2= JButton("Изменить цвет производной")

    private var color1= Color.BLUE
    fun getColor1():Color {return color1}

    private var color2= Color.RED
    fun getColor2():Color {return color2}

    companion object{  // В с# были такие же, но static
        private val MIN_SZ = GroupLayout.PREFERRED_SIZE                                                                                                  // для примера: если это будет лейбол то займет все значение
        private val MAX_SZ = GroupLayout.DEFAULT_SIZE                                                                                                    // не больше чем обозначеная размер.  //для все окошек панелей  , константы для всех обьектов
    }

    private val valChangeListeners = mutableListOf<()->Unit>()  // Создаём пустой список и добавим ему пару методов
    private val colorListeners = mutableListOf<()->Unit>()  // Кнопочки с цветом

    init{
        border = EtchedBorder()

        lXMin = JLabel()
        lXMax = JLabel()
        lYMin = JLabel()
        lYMax = JLabel()
        lXMin.text = "Xmin:"  // текст около скролла
        lXMax.text = "Xmax:"  // текст около скролла
        lYMin.text = "Ymin:"  // текст около скролла
        lYMax.text = "Ymax:"  // текст около скролла

        // sm - спинэр модул
        smXMin = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)  // скролл
        smXMax = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)  // скролл
        smYMin = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)  // скролл
        smYMax = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)  // скролл

        // По сколько прибавлять при вращении
        smXMin.addChangeListener{
            smXMax.minimum = smXMin.number.toDouble() + 0.1  // скролл
            valChangeListeners.forEach { it() }  // вызываем для списка (берем список листнеров (т е функций сохранённых в списки обработчиков событий изменений спинеров) , пробегаемся по всему списку и для каждого элемента списка выполняем его вызов)
        }
        smXMax.addChangeListener{
            smXMin.maximum = smXMax.number.toDouble() - 0.1  // скролл
            valChangeListeners.forEach { it() }
        }
        smYMin.addChangeListener{
            smXMax.minimum = smYMin.number.toDouble() + 0.1  // скролл
            valChangeListeners.forEach { it() }
        }
        smYMax.addChangeListener{
            smXMax.minimum = smYMax.number.toDouble() - 0.1  // скролл
            valChangeListeners.forEach { it() }
        }

        chButton1.addActionListener {  // Кнопочки с цветом
            run {
                color1 = JColorChooser.showDialog(this, "Select a color", Color.RED)
                colorListeners.forEach { l -> l() }
            }
        }
        chButton2.addActionListener {  // Кнопочки с цветом
            run {
                color2 = JColorChooser.showDialog(this, "Select a color", Color.RED)
                colorListeners.forEach { l -> l() }
            }
        }

        sXMin = JSpinner(smXMin)  // скролл
        sXMax = JSpinner(smXMax)  // скролл
        sYMin = JSpinner(smYMin)  // скролл
        sYMax = JSpinner(smYMax)  // скролл

        val gl = GroupLayout(this)
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(4)  // отступ
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(lXMin, MIN_SZ, MIN_SZ, MIN_SZ)
                                .addComponent(sXMin, MIN_SZ, MIN_SZ, MIN_SZ)  // скролл
                                .addComponent(lXMax, MIN_SZ, MIN_SZ, MIN_SZ)
                                .addComponent(sXMax, MIN_SZ, MIN_SZ, MIN_SZ)  // скролл
                )
                .addGap(8)
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(lYMin, MIN_SZ, MIN_SZ, MIN_SZ)
                                .addComponent(sYMin, MIN_SZ, MIN_SZ, MIN_SZ)  // скролл
                                .addComponent(lYMax, MIN_SZ, MIN_SZ, MIN_SZ)
                                .addComponent(sYMax, MIN_SZ, MIN_SZ, MIN_SZ)  // скролл
                )
                .addGap(8)
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(chButton1,MIN_SZ,MIN_SZ,MIN_SZ)  // Кнопочки с цветом
                                .addComponent(chButton2,MIN_SZ,MIN_SZ,MIN_SZ)  // Кнопочки с цветом
                )
                .addGap(4)
        )

        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(4)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lXMin, MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(lYMin, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGap(2)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(chButton1,MIN_SZ,MIN_SZ,MIN_SZ)  // Кнопочки с цветом
                                        .addComponent(sXMin, MIN_SZ, 100, MAX_SZ)  // скролл
                                        .addComponent(sYMin, MIN_SZ, 100, MAX_SZ)  // скролл
                        )
                        .addGap(8, 8, Int.MAX_VALUE)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lXMax, MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(lYMax, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGap(2)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(chButton2,MIN_SZ,MIN_SZ,MIN_SZ)  // Кнопочки с цветом
                                        .addComponent(sXMax, MIN_SZ, 100, MAX_SZ)  // скролл
                                        .addComponent(sYMax, MIN_SZ, 100, MAX_SZ)  // скролл
                        )
                        .addGap(4)
        )
        layout = gl
    }

    fun addValChangeListener(l: ()->Unit){  // для списка
        valChangeListeners.add(l)
    }

    fun addColorListener(l: () -> Unit){   // Кнопочки с цветом
        colorListeners.add(l)
    }
}