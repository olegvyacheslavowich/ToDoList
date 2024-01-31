package ru.elipson.todolist.domain

class GetToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun getToDoItem(id: Int): ToDoItem? = toDoListRepository.get(id)
}