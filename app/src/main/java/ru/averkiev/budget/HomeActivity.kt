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
            Chat("Тони", R.drawable.profile_1, "Привет, когда рожать собираетесь?", "12:00"),
            Chat("Стас", R.drawable.profile_2, "Как проходит твой зачёт?", "12:05"),
            Chat("Валера", R.drawable.profile_3, "Спасибо, за шоколадку!", "12:15"),
            Chat("Маша", R.drawable.profile_4, "Маш, ты утюг выключила?", "12:30")
        )

        // Установка адаптера
        recyclerView.adapter = ChatAdapter(chatList)
    }
}