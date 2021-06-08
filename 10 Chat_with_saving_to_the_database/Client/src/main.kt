import java.awt.Dimension
import java.awt.TextArea
import java.awt.TextField
import java.awt.event.KeyListener
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.system.exitProcess

fun main() {
    val client = ClientWindow()
    client.isVisible = true
}


class ClientWindow() : JFrame() {
    init {
        val client = Client("localhost", 5804)
        client.start()
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Клиент"
        val textArea = TextArea()
        val textField = TextField()
        textArea.isEditable = false
        textField.minimumSize = Dimension(100, 26)
        textField.maximumSize = Dimension(400, 26)


        val btn = JButton("Отправить")
        btn.addActionListener {
            textArea.append("> ${textField.text}\n")
            client.send(textField.text)
            textField.text = ""
        }
        val minSize = Dimension(550,400)
        val mainPanel = JPanel()

        val gl  = GroupLayout(mainPanel).apply{
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(5)
                    .addComponent(textArea,minSize.height,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    .addGap(5)
                    .addGroup(createParallelGroup()
                        .addComponent(textField,26,26,26)
                        .addGap(5)
                        .addComponent(btn,26,26,26)
                    )
                    .addGap(5)
            )
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(5)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(textArea,minSize.width,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addGap(5)
                            .addGroup(
                                createSequentialGroup()
                                    .addComponent(textField,400,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                    .addGap(5)
                                    .addComponent(btn,100,GroupLayout.DEFAULT_SIZE,100)
                            )
                    )
                    .addGap(5)
            )
        }


        mainPanel.add(textArea)
        mainPanel.add(textField)
        mainPanel.add(btn)
        mainPanel.layout = gl
        add(mainPanel)
        pack()
        println(textField.size)

        client.addSessionFinishedListener {
            textArea.append("Работа с сервером завершена. Нажмите Enter для выхода...\n")
            exitProcess(0)
        }
        client.addMessageListener { textArea.append(it + "\n") }

    }
}
