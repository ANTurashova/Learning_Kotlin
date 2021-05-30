package ru.anna.gui.graphics.convertation

//data class CartesianPlane(  // практически то же самое что CartesianScreenPlane, но без фактических размеров в пикселях
//        var xMin: Double,
//        var xMax: Double,
//        var yMin: Double,
//        var yMax: Double
//)

data class CartesianScreenPlane(  // data class - его основное назначение это хранить информацию.
        var realWidth:   Int,  // реальная ширина, сколько пикселей умещается в панель
        var realHeight:  Int,  // реальная высота
        var xMin: Double,  // концы отрезков декартовой системы координат, которые я собираюсь отображать в панели
        var xMax: Double,
        var yMin: Double,
        var yMax: Double
) {  // размещённые ниже поля вычесляемые. Они не имеют бекенд филд, т е их значение зависит от тех основных полей которые указаны в конструкторе
    val width: Int  // уменьшаем на 1, т к если будем рисать в последнем пикселе, его номер на 1 меньше максимаотного (пиксели от 0 до max)
        get() = realWidth - 1
    val height: Int
        get() = realHeight - 1
    val xDen: Double  // Плотность = кол-во пикселей * единицу декартовой системы координат
        get() = width.toDouble() / (xMax - xMin)
    val yDen: Double
        get() = height.toDouble() / (yMax - yMin)
}
