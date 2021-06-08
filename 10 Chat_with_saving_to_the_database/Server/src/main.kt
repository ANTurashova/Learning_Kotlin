import java.util.*

fun main() {
    val s = Server()

    val dbController = DBController("chat")

    s.addMessageListener { id, data ->
        if (id == 0)
            println("[SERVER] $data")
        else {
            println("[$id] $data")
            dbController.addMessage(id, data)
        }
    }

    s.start()
    var cmd: String
    val sc = Scanner(System.`in`)
    do {
        cmd = sc.nextLine()
    } while (cmd != "STOP")
    s.stop()
}

