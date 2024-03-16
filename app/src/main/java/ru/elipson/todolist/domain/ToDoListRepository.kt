package ru.elipson.todolist.domain

import androidx.lifecycle.LiveData

interface ToDoListRepository {

    fun getListLiveData(): LiveData<List<ToDoItem>>
    suspend fun get(id: Int): ToDoItem?

    suspend fun delete(item: ToDoItem)

    suspend fun edit(item: ToDoItem)

    suspend fun add(item: ToDoItem)

}