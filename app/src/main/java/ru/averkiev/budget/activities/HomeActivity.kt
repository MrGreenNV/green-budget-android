package ru.averkiev.budget.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.model.Chat
import ru.averkiev.budget.utils.ChatAdapter
import ru.averkiev.budget.R

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