package ru.averkiev.budget.models

data class Chat(
    val senderName: String,
    val senderProfile: Int,
    val lastMessage: String,
    val timeSent: String
)