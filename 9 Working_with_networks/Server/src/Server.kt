import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class Server(val port: Int = 5804){
    private val sSocket: ServerSocket

    init {
        sSocket = ServerSocket(port)
    }

    fun start(){
        val acceptedClient = sSocket.accept() //принять соединение, = будет возвращать клиенткий сокет
        // для обмена данных по сети удобно работать с класслами для чтения
        val br = BufferedReader(InputStreamReader(acceptedClient.getInputStream()))
        //читаем методом ридлайн
        val clientQuery = br.readLine()
        println("Client send: \"$clientQuery\"")
        //и отправим что-нибудь
        val pw = PrintWriter(acceptedClient.getOutputStream())
        pw.println("You were successfully connected.")
        //флаш очищает буфер и из буфера всю информацию скидывает в сеть
        pw.flush()
        //закрываю сокет
        acceptedClient.close()
    }
}