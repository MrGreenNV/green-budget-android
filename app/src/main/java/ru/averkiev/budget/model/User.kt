package ru.averkiev.budget.model

import java.io.Serializable

class User(val login: String, val email: String, val pass: String) : Serializable {
}