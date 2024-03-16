package ru.elipson.todolist.domain

class EditToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun editToDoItem(item: ToDoItem) {
        toDoListRepository.edit(item)
    }

}