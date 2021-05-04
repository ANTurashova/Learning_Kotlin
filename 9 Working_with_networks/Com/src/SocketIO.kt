import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import kotlin.concurrent.thread

class SocketIO(val socket: Socket) {
    private var stop = false

    fun stop(){
        stop = true
    }

    fun startDataReceiving(){
        stop = false
        stopAll = false
        thread {
            val br = BufferedReader(InputStreamReader(socket.getInputStream()))
            while (!stop && !stopAll){
                val data = br.readLine()
                println(data) //////////////

            }
            socket.close()
        }
    }

    fun sendData(data: String){
        //
    }

    companion object{
        private var stopAll  = false
        fun stopAll(){
            stopAll = true
        }
    }

}