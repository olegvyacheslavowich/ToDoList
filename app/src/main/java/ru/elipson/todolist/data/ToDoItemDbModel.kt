package ru.elipson.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_item")
data class ToDoItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val description: String,
    val enabled: Boolean,
    val day: Long
)
