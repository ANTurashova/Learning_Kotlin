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



//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.PrintWriter
//import java.net.ServerSocket
//import java.net.Socket
//import kotlin.concurrent.thread
//
//class Server(val port: Int = 5804){
//    private val sSocket: ServerSocket
//    private val clients = mutableListOf<Client>()
//    private var stop = false // флажок стоп
//
//    inner class Client(val socket: Socket){
//        fun startDialog(){
//            thread { //подпроцесс
//                SocketIO(socket).startDataReceiving()
//            }
//        }
//    }
//
//    init {
//        sSocket = ServerSocket(port)
//    }
//
//    fun stop(){
//        stop = true
//    }
//
//    fun start(){
//        stop = false
//        //val acceptedClient = sSocket.accept() //принять соединение, = будет возвращать клиенткий сокет
//        thread { // подпроцесс
//            while (!stop) {
//                clients.add(Client(sSocket.accept()).also {client -> client.startDialog()})
//            }
//        }
//
//
//
//        // для обмена данных по сети удобно работать с класслами для чтения
//        val br = BufferedReader(InputStreamReader(acceptedClient.getInputStream()))
//        //читаем методом ридлайн
//        val clientQuery = br.readLine()
//        println("Client send: \"$clientQuery\"")
//        //и отправим что-нибудь
//        val pw = PrintWriter(acceptedClient.getOutputStream())
//        pw.println("You were successfully connected.")
//        //флаш очищает буфер и из буфера всю информацию скидывает в сеть
//        pw.flush()
//        //закрываю сокет
//        acceptedClient.close()
//    }
//}