package ru.anna

class Newton(xfx: MutableMap<Double, Double>) : Polynom(){

    val xfx = xfx.toMutableMap()

    init{ // Конструктор
        val p = Polynom()
        xfx.forEach {
            println("xfx.forEach: $it")
//            val r = fundamental(it.key)
//            if (r != null) p += r * it.value
//            else return@forEach
        }
        coef = p.coefficients




        println("-----------------------------------------------")
        val helping: MutableList<Double> = mutableListOf(0.0, 0.0)
        var str = ""
        helping[0] = xfx.values.elementAt(0)
        helping[1] = (xfx.values.elementAt(1) - xfx.values.elementAt(0))/(xfx.keys.elementAt(1) - xfx.keys.elementAt(0))
        str = "${helping[0]} + ${helping[0]}(x - ${xfx.keys.elementAt(0)})"
        println(str)
        println("-----------------------------------------------")

//        println(p.coefficients[0])
//        println(p.coefficients[1])
//        println(p.coefficients[2])
    }

    /**
     * Вычисление слогаемых в полиноме Ньютона
     */



}