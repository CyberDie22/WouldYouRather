package me.cyberdie22.wouldyourather

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowEvent
import javax.swing.*
import kotlin.reflect.KProperty

//language=json
const val defaultData = """
{
  "name": "Test",
  "questions": [
    {
      "name": "Test Question",
      "answers": [
        "Test1",
        "Test2"
      ]
    }
  ]
}
"""

fun <T : Component> scrollable(initializer: JScrollPane.() -> Unit) = Scrollable<T>(initializer)

class Scrollable<T : Component>(initializer: JScrollPane.() -> Unit) {

    private val scrollPane = JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)

    init {
        scrollPane.initializer()
    }

    operator fun getValue(thisReg: Any?, property: KProperty<*>): JScrollPane {
        @Suppress("UNCHECKED_CAST")
        return scrollPane
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        scrollPane.remove(3)
        scrollPane.add(value)
    }
}

class QuestionPanel(question: Question, onAnswerClick: (Answer) -> Unit) : JPanel(BorderLayout()) {
    init {
        val name = JLabel(question.name)
        add(name)
        name.preferredSize = Dimension(name.height, width)
        question.answers.forEach {
            val answer = JButton(it.text)
            answer.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) = onAnswerClick(it)
            })
            add(answer)
        }
    }
}

class WouldYouRather(val gameConfig: GameConfig = Json.decodeFromString(defaultData)) : JFrame("Would You Rather") {
    private var questionIndex = 0

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(800, 600)
        showCurrentQuestion()
    }

    private fun showCurrentQuestion() {
        removeAll()
//        val questionPanel by scrollable<QuestionPanel> {
            val questionPanel = QuestionPanel(gameConfig.questions[questionIndex]) {
                questionIndex++
                showCurrentQuestion()
            }
            add(questionPanel)
//        }
//        add(questionPanel)
    }

    fun start() {
        isVisible = true
    }

    fun stop() {
        isVisible = false
    }

    fun close() {
        stop()
        dispatchEvent(WindowEvent(this, WindowEvent.WINDOW_CLOSING))
    }
}

fun main() {
    println("Starting WouldYouRather app...")
    val app = WouldYouRather()
    app.start()
}