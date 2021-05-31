import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class Server(val port: Int = 5804) {
    private val sSocket: ServerSocket
    private val clients = mutableListOf<Client>()
    private var stop = false

    inner class Client(val socket: Socket){
        fun startDialog(){
            SocketIO(socket).apply{
                addSocketClosedListener {
                    clients.remove(this@Client)
                }
                startDataReceiving()
            }
        }
    }

    init{
        sSocket = ServerSocket(port)
    }

    fun stop(){
        stop = true
    }

    fun start() {
        stop = false
        thread {
            while (!stop) {
                clients.add(Client(sSocket.accept()).also { client -> client.startDialog() })
            }
        }
    }

}