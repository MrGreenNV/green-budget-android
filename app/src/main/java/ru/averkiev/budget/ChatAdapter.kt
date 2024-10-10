package ru.averkiev.budget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chatList: List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val senderProfile: ImageView = itemView.findViewById(R.id.ivSenderProfile)
        val lastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        val timeSent: TextView = itemView.findViewById(R.id.tvTimeSent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.senderName.text = chat.senderName
        holder.senderProfile.setImageResource(chat.senderProfile)
        holder.lastMessage.text = chat.lastMessage
        holder.timeSent.text = chat.timeSent
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}
