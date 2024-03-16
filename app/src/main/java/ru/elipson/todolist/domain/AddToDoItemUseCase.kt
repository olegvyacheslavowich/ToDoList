package ru.elipson.todolist.domain

class AddToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun addToDoItem(item: ToDoItem) {
        toDoListRepository.add(item)
    }

}