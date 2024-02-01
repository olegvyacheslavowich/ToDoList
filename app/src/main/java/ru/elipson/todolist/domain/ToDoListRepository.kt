package ru.elipson.todolist.domain

import androidx.lifecycle.LiveData

interface ToDoListRepository {

    fun getListLiveData(): LiveData<List<ToDoItem>>
    fun getList(): List<ToDoItem>
    fun get(id: Int): ToDoItem?

    fun delete(item: ToDoItem)

    fun edit(item: ToDoItem)

    fun add(item: ToDoItem)

}