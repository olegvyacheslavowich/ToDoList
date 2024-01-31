package ru.elipson.todolist.domain

interface ToDoListRepository {

    fun getList(): List<ToDoItem>
    fun get(id: Int): ToDoItem?

    fun delete(item: ToDoItem)

    fun edit(item: ToDoItem)

    fun add(item: ToDoItem)

}