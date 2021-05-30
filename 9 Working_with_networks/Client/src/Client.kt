import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class Client(
        val host: String,
        val port: Int
) {
    private val socket: Socket
    private val communicator: SocketIO
    init{
        socket = Socket(host, port)
        communicator = SocketIO(socket)
    }

    fun stop(){
        communicator.stop()
    }

    fun start(){
        communicator.startDataReceiving()
    }

    fun send(data: String) {
        communicator.sendData(data)
    }
}

//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.PrintWriter
//import java.net.Socket
//
//class Client(
//    val host: String,
//    val port: Int
//){
//    private val socket: Socket
//    private var stop -
//    init{
//        socket = Socket(host, port)
//    }
//
//    fun stop(){
//        stop = true
//    }
//
//    fun start(){
//        val pw = PrintWriter(socket.getOutputStream())
//        pw.println("((((((")
//        pw.flush() // в сеть скидываем
//        val br = BufferedReader(InputStreamReader(socket.getInputStream()))
//        val response = br.readLine()
//        println("Server response: \"$response\"")
//        socket.close()
//    }
//}