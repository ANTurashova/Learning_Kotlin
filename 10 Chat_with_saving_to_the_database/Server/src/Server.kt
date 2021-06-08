import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.net.Socket

class Server(port: Int = 5804) {
    private val sSocket: ServerSocket = ServerSocket(port)
    private val clients = mutableListOf<Client>()
    private var stop = false

    private val messageListeners = mutableListOf<(Int, String) -> Unit>()
    fun addMessageListener(l: (Int, String) -> Unit) {
        messageListeners.add(l)
    }

    fun removeMessageListener(l: (Int, String) -> Unit) {
        messageListeners.remove(l)
    }

    // вложенный класс клиентов, чтобы сервер хранил 'копии' подключенных клиентов
    // и взаимодействовал с подключенными клиентами
    inner class Client(private val socket: Socket) {
        private var sio: SocketIO? = null
        private val id: Int = clients.size + 1
        // Обработка информации с подключенными клиентами
        fun startDialog() {
            sio = SocketIO(socket).apply {
                addSocketClosedListener {
                    clients.remove(this@Client)
                }
                addMessageListener { data ->
                    messageListeners.forEach { l -> l(id, data) }
                    clients.forEach { client ->
                        if (client != this@Client) client.send("[$id] $data")
                    }
                }
                startDataReceiving()
            }
        }

        // Остановка подключений
        fun stop() {
            sio?.stop()
        }

        private fun send(data: String) {
            sio?.send(data)
        }
    }

    // Закрытие сокет сервера
    fun stop() {
        sSocket.close()
        stop = true
    }

    // Старт сервера
    fun start() {
        messageListeners.forEach { l -> l(0, "Сервер запущен.") }
        stop = false
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                while (!stop) {
                    clients.add(
                        Client(
                            sSocket.accept()
                        ).also { client -> client.startDialog() })
                }
            } catch (e: Exception) {
                messageListeners.forEach { l ->
                    e.message?.let { l(0, it) }
                }
            } finally {
                stopAllClients()
                sSocket.close()
                messageListeners.forEach { l ->
                    l(0, "Сервер остановлен.")
                }
            }
        }
    }

    // Остановка всех клиентов
    private fun stopAllClients() {
        clients.forEach { client -> client.stop() }
    }
}