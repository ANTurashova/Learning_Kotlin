import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SocketIO(private val socket: Socket) {

    private var stop = false

    //Событие, которое возникает при закрытии подключения
    private val socketClosedListener = mutableListOf<() -> Unit>()
    fun addSocketClosedListener(l: () -> Unit) {
        socketClosedListener.add(l)
    }

    fun removeSocketClosedListener(l: () -> Unit) {
        socketClosedListener.remove(l)
    }

//    private var thread: Thread? = null

    fun removeMessageListener(l: (String) -> Unit) {
        messageListeners.remove(l)
    }

    private val messageListeners = mutableListOf<(String) -> Unit>()
    fun addMessageListener(l: (String) -> Unit) {
        messageListeners.add(l)
    }

    private val exceptionListeners = mutableListOf<(String) -> Unit>()
    fun addExceptionListener(l: (String) -> Unit) {
        exceptionListeners.add(l)
    }
    fun removeExceptionListener(l: (String) -> Unit) {
        exceptionListeners.remove(l)
    }


    // разрыв соедения, закрытие сокетов
    fun stop() {
        stop = true
        socket.close()
    }

    // Обработка полученной информации
    fun startDataReceiving() {
        stop = false
//        thread{
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                val br = BufferedReader(InputStreamReader(socket.getInputStream()))
                while (!stop) {
                    val data = br.readLine()
                    if (data != null)
                        messageListeners.forEach { l -> l(data) }
//                        println(data)
                    else {
                        throw IOException("Связь прервалась")
                    }
                }
            } catch (ex: Exception) {
                exceptionListeners.forEach { l -> ex.message?.let { l(it) } }
//                socket.close()
//                println(ex.message)
            } finally {
                socket.close()
                socketClosedListener.forEach { it() }
            }
        }
    }

    // Отправка данных подключению
    fun send(data: String): Boolean {
        return try {
            val pw = PrintWriter(socket.getOutputStream())
            pw.println(data)
            pw.flush()
            true
        } catch (ex: Exception) {
            false
        }
    }

}