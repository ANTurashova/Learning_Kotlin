import java.net.Socket

class Client(
    private val host: String,
    private val port: Int
) {
    private val socket: Socket = Socket(host, port)
    private val communicator: SocketIO = SocketIO(socket)

    private val messageListeners = mutableListOf<(String) -> Unit>()
    fun addMessageListener(l: (String) -> Unit) {
        messageListeners.add(l)
    }

    fun removeMessageListener(l: (String) -> Unit) {
        messageListeners.remove(l)
    }

    fun stop() {
        communicator.stop()
    }

    fun start() {
        communicator.addMessageListener {
            messageListeners.forEach { l -> l(it) }
        }
        communicator.startDataReceiving()
    }

    fun send(data: String) {
        communicator.send(data)
    }

    fun addSessionFinishedListener(l: () -> Unit) {
        communicator.addSocketClosedListener(l)
    }

    fun removeSessionFinishedListener(l: () -> Unit) {
        communicator.removeSocketClosedListener(l)
    }
}