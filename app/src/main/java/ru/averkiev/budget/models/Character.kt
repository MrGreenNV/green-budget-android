package ru.averkiev.budget.models

import kotlinx.serialization.Serializable

@Serializable
data class Character (
    val name: String?,
    val gender: String?,
    val culture: String?,
    val born: String?,
    val titles: List<String>?,
    val aliases: List<String>?,
    val playedBy: List<String>?
)