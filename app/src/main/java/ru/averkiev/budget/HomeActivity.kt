package ru.averkiev.budget

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewChats)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Фиктивные данные для чатов
        val chatList = listOf(
            Chat("Иван Иванов", R.drawable.profile1, "Привет, как дела?", "12:00"),
            Chat("Ольга Петрова", R.drawable.profile2, "Завтра встретимся?", "12:05"),
            Chat("Мария Смирнова", R.drawable.profile3, "Отправила отчет", "12:15"),
            Chat("Алексей Сидоров", R.drawable.profile4, "Спасибо!", "12:30")
        )

        // Установка адаптера
        recyclerView.adapter = ChatAdapter(chatList)
    }
}