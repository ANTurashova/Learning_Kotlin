import java.util.regex.Pattern

class RegExTest(val wholeText: String) {
    fun find(regex: String): List<String>{
        val p = Pattern.compile(
                regex,
                Pattern.MULTILINE + // MULTILINE - обрабатывать многострочный текст
                Pattern.UNICODE_CHARACTER_CLASS +
                Pattern.UNICODE_CASE
        )
        val m = p.matcher(wholeText)
        val result = mutableListOf<String>()
        while (m.find()) {   // find - метод "найти"
            val s = m.start()  // позиция начала выделенного фрагмента текста
            val e = m.end()  // позиция конца
            result.add(wholeText.substring(s until e))  // с помощью метода substring можем получить подстроку, в котором укажем диапозон символов.
            // добавляем это в список resuln // until потому что не нужно включать последний символ
        }
        return result
    }
}

fun main(){
    val s = "Какой-то текст, в котором нужно что-то найти."
    val result = RegExTest(s).find("[Нн]\\w*")  // слова начинающиеся на букву "н"
    result.forEach{println(it)}
}


// нужно выделлить время и даду и не выделить ничего другого
// список дат и времён, который в этом тексте есть 