package ru.elipson.todolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.elipson.todolist.data.ToDoListRepositoryImpl
import ru.elipson.todolist.domain.DeleteToDoItemUseCase
import ru.elipson.todolist.domain.EditToDoItemUseCase
import ru.elipson.todolist.domain.GetToDoListUseCase
import ru.elipson.todolist.domain.ToDoItem

class ToDoListViewModel : ViewModel() {

    private val toDoListRepository = ToDoListRepositoryImpl

    private val getToDoListUseCase = GetToDoListUseCase(toDoListRepository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(toDoListRepository)
    private val editToDoListUseCase = EditToDoItemUseCase(toDoListRepository)

    private val _toDoListLiveData = MutableLiveData<List<ToDoItem>>()
    val toDoListLiveData: LiveData<List<ToDoItem>> get() = _toDoListLiveData

    fun loadToDoList() {
        _toDoListLiveData.value = getToDoListUseCase.getToDoList()
    }

    fun deleteToDoItem(item: ToDoItem) {
        deleteToDoItemUseCase.deleteToDoItem(item)
        loadToDoList()
    }

    fun changeEnabled(item: ToDoItem) {
        editToDoListUseCase.editToDoItem(
            item.copy(enabled = !item.enabled)
        )
        loadToDoList()
    }

}