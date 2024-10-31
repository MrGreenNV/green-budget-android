package ru.averkiev.budget.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.averkiev.budget.models.User

class MyViewModel: ViewModel() {
    val userData= MutableLiveData<User>()
}