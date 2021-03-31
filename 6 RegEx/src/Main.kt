import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
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
    val s = loadText("3k.txt")
    val result = RegExTest(s).find("""
        (?<=\s)(?:(?:(?:[0-2]?\d|31)\.(?:0\d|1[0-2]))\.
        (?:(?:[1-2]\d)?\d\d)|(?:(?:[0-2]?\d|1[0-2]))\
        /(?:[0-2]?\d|31)\/(?:(?:[1-2]\d)?\d\d)) 
        ((?:[0-1]?\d|2[0-3]):[0-5]\d(?:\:[0-5]\d)?)
        (?=\s)
    """.trimIndent())  // слова начинающиеся на букву "н"
    result.forEach{println(it)}
}

fun loadText(filename: String): String {
    val f = BufferedReader(
        InputStreamReader(
            FileInputStream(
                filename
            )
        )
    )
    val res = f.readLines().joinToString(separator = "\n")
    f.close()
    return res
}


// нужно выделлить время и даду и не выделить ничего другого
// список дат и времён, который в этом тексте есть