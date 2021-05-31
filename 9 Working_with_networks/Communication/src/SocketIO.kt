import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket
import kotlin.concurrent.thread

class SocketIO(val socket: Socket) {

    private var stop = false
    private var thread: Thread? = null
    private val socketClosedListener = mutableListOf<()->Unit>()

    fun addSocketClosedListener(l: ()->Unit){
        socketClosedListener.add(l)
    }

    fun removeSocketClosedListener(l: ()->Unit){
        socketClosedListener.remove(l)
    }

    fun stop(){
        stop = true
        socket.close()
    }

    fun startDataReceiving() {
        stop = false
        stopAll = false
        thread = thread{
            try {
                val br = BufferedReader(InputStreamReader(socket.getInputStream()))
                while (!stop && !stopAll) {
                    val data = br.readLine()
                    if (data!=null)
                        println(data)
                    else {
                        throw IOException("Связь прервалась")
                    }
                }
            } catch (ex: Exception){
                socket.close()
                socketClosedListener.forEach{it()}
                println(ex.message)
            }
        }
    }

    fun sendData(data: String): Boolean{
        try {
            val pw = PrintWriter(socket.getOutputStream())
            pw.println(data)
            pw.flush()
            return true
        } catch (ex: Exception){
            return false
        }
    }

    companion object{
        private var stopAll = false
        fun stopAll(){
            stopAll = true
        }
    }
}