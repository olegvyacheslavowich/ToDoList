package ru.elipson.todolist.domain

class GetToDoListUseCase(private val toDoListRepository: ToDoListRepository) {
    fun getToDoList(): List<ToDoItem> = toDoListRepository.getList()
}