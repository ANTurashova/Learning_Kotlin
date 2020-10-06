package ru.anna

class Newton(private var xfx: ArrayList<Pair<Double, Double>>): Polynom() {
    private val divDiff: MutableMap<Pair<Int, Int>, Double> = mutableMapOf()

    init {                                                                                                                 // сделать через newPoint
        var fundamental = Polynom(doubleArrayOf(1.0))
        val p = Polynom()

        for (k in 0 until xfx.size) {
            for (i in 0..k) {
                if (i != 0) fundamental *= Polynom(doubleArrayOf(-xfx[i - 1].first, 1.0))
            }
            p += fundamental * divD(0, k)
        }
        coef = p.coeffitients
    }

    private fun divD(one: Int, two: Int): Double {
        if (two - one == 0) {     //(1,1) - это f(x1)
            divDiff[Pair(one, one)] = xfx[two].second
            return xfx[two].second
        }
        val result = (divD(one + 1, two) - divD(one, two - 1)) / (xfx[two].first - xfx[one].first)
        divDiff[Pair(one, two)] = result
        return result
    }

    fun newPoint(key: Double, value: Double) {
        var fundamental = Polynom(doubleArrayOf(1.0))
        val p = Polynom(coef)

//        xfx.forEach () {
//            println("--- Точки: $it")
//            if (key == it.first) {
//                println("Вы ввели одинаковые точки")
                  // Не добавлять точку
//            }
//            else {
//                // Добавлять точку
//            }
//        }

        if (xfx.find {it.first == key } == null){
            xfx.add(Pair(key, value))
            for (i in 0 until xfx.size)
                if (i != 0)
                    fundamental *= Polynom(doubleArrayOf(-xfx[i - 1].first, 1.0))

            p += fundamental * divD(0, xfx.size - 1)
            coef = p.coeffitients
        }
    }
}






