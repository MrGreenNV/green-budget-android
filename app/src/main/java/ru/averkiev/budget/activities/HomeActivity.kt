package ru.averkiev.budget.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.R
import ru.averkiev.budget.model.Chat
import ru.averkiev.budget.utils.ChatAdapter

class HomeActivity : AppCompatActivity() {

    companion object {
        const val TAG_NAME = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_NAME, "started 'onCreate'")
        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewChats)
        val loginTextView: TextView = findViewById(R.id.logName)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loginTextView.text = intent.getStringExtra("loginName") ?: "???"

        val chatList = listOf(
            Chat("Тони", R.drawable.profile_1, "Привет, когда рожать собираетесь?", "12:00"),
            Chat("Стас", R.drawable.profile_2, "Как проходит твой зачёт?", "12:05"),
            Chat("Валера", R.drawable.profile_3, "Спасибо, за шоколадку!", "12:15"),
            Chat("Маша", R.drawable.profile_4, "Маш, ты утюг выключила?", "12:30")
        )

        recyclerView.adapter = ChatAdapter(chatList)
    }

    override fun onStart() {
        Log.d(TAG_NAME, "started 'onStart'")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG_NAME, "started 'onResume'")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG_NAME, "started 'onPause'")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG_NAME, "started 'onStop'")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG_NAME, "started 'onDestroy'")
        super.onDestroy()
    }
}