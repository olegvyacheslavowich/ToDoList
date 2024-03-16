package ru.elipson.todolist.domain

import java.util.Date

data class ToDoItem(
    val name: String,
    val description: String,
    val enabled: Boolean,
    val day: Date,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
