package ru.elipson.todolist.domain

class GetToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun getToDoItem(id: Int): ToDoItem? = toDoListRepository.get(id)
}