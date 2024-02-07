package ru.elipson.todolist.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.elipson.todolist.domain.ToDoItem

class ToDoListDiffItemCallback : DiffUtil.ItemCallback<ToDoItem>() {
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean =
        oldItem == newItem
}