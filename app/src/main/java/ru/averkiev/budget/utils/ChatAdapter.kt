package ru.averkiev.budget.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.averkiev.budget.databinding.ItemChatBinding
import ru.averkiev.budget.models.Chat

class ChatAdapter(private val chatList: List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]

        holder.binding.tvSenderName.text = chat.senderName
        holder.binding.ivSenderProfile.setImageResource(chat.senderProfile)
        holder.binding.tvLastMessage.text = chat.lastMessage
        holder.binding.tvTimeSent.text = chat.timeSent
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}
