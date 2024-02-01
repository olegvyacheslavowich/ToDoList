package ru.elipson.todolist.domain

import androidx.lifecycle.LiveData

class GetToDoListUseCase(private val toDoListRepository: ToDoListRepository) {
    fun getToDoList(): LiveData<List<ToDoItem>> = toDoListRepository.getListLiveData()
}