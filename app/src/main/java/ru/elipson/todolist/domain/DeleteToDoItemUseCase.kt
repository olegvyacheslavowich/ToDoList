package ru.elipson.todolist.domain

class DeleteToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun deleteToDoItem(item: ToDoItem) {
        toDoListRepository.delete(item)
    }

}