package ru.averkiev.budget.models

import java.io.Serializable

class User(val login: String, val email: String, val pass: String) : Serializable {
}