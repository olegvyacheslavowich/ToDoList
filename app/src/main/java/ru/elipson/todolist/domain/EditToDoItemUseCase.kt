package ru.elipson.todolist.domain

class EditToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun editToDoItem(item: ToDoItem) {
        toDoListRepository.edit(item)
    }

}