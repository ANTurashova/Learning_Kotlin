// Нижняя панель со скороллом координат, бывшая LagrangeControl
package ru.anna.gui.components

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

    private val smXMin: SpinnerNumberModel  // скролл
    private val smXMax: SpinnerNumberModel  // скролл
    private val smYMin: SpinnerNumberModel  // скролл
    private val smYMax: SpinnerNumberModel  // скролл

    companion object{
        private val MIN_SZ = GroupLayout.PREFERRED_SIZE
        private val MAX_SZ = GroupLayout.DEFAULT_SIZE
    }

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
        smXMin.addChangeListener{ smXMax.minimum = smXMin.number.toDouble() + 0.1}  // скролл
        smXMax.addChangeListener{ smXMin.maximum = smXMax.number.toDouble() - 0.1}  // скролл
        smYMin.addChangeListener{ smXMax.minimum = smYMin.number.toDouble() + 0.1}  // скролл
        smYMax.addChangeListener{ smXMax.minimum = smYMax.number.toDouble() - 0.1}  // скролл

        sXMin = JSpinner(smXMin)  // скролл
        sXMax = JSpinner(smXMax)  // скролл
        sYMin = JSpinner(smYMin)  // скролл
        sYMax = JSpinner(smYMax)  // скролл

        val gl = GroupLayout(this)
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(4)
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
                                        .addComponent(sXMax, MIN_SZ, 100, MAX_SZ)  // скролл
                                        .addComponent(sYMax, MIN_SZ, 100, MAX_SZ)  // скролл
                        )
                        .addGap(4)
        )
        layout = gl
    }
}