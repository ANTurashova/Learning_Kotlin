import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    val host: String,
    val port: Int
){
    private val socket: Socket
    init{
        socket = Socket(host, port)
    }

    fun start(){
        val pw = PrintWriter(socket.getOutputStream())
        pw.println("((((((")
        pw.flush() // в сеть скидываем
        val br = BufferedReader(InputStreamReader(socket.getInputStream()))
        val response = br.readLine()
        println("Server response: \"$response\"")
        socket.close()
    }
}