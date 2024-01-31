package ru.elipson.todolist.domain

class DeleteToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun deleteToDoItem(item: ToDoItem) {
        toDoListRepository.delete(item)
    }

}