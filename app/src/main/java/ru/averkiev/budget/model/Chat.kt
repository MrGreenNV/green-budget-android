package ru.averkiev.budget.model

data class Chat(
    val senderName: String,
    val senderProfile: Int,
    val lastMessage: String,
    val timeSent: String
)