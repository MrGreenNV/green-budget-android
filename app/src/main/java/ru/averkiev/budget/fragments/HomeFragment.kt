package ru.averkiev.budget.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.R
import ru.averkiev.budget.models.Chat
import ru.averkiev.budget.utils.ChatAdapter
import ru.averkiev.budget.utils.MyViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginTextView: TextView = view.findViewById(R.id.logName)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewChats)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        viewModel.loginName.observe(viewLifecycleOwner) { loginName ->
            loginTextView.text = loginName
        }

        val chatList = listOf(
            Chat("Тони", R.drawable.profile_1, "Привет, когда рожать собираетесь?", "12:00"),
            Chat("Стас", R.drawable.profile_2, "Как проходит твой зачёт?", "12:05"),
            Chat("Валера", R.drawable.profile_3, "Спасибо, за шоколадку!", "12:15"),
            Chat("Маша", R.drawable.profile_4, "Маш, ты утюг выключила?", "12:30")
        )

        recyclerView.adapter = ChatAdapter(chatList)
    }
}