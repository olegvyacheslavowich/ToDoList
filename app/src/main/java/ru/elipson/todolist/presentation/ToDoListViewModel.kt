package ru.elipson.todolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elipson.todolist.data.ToDoListRepositoryImpl
import ru.elipson.todolist.domain.AddToDoItemUseCase
import ru.elipson.todolist.domain.DeleteToDoItemUseCase
import ru.elipson.todolist.domain.EditToDoItemUseCase
import ru.elipson.todolist.domain.GetToDoListUseCase
import ru.elipson.todolist.domain.ToDoItem

class ToDoListViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoListRepository = ToDoListRepositoryImpl(application)

    private val getToDoListUseCase = GetToDoListUseCase(toDoListRepository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(toDoListRepository)
    private val editToDoListUseCase = EditToDoItemUseCase(toDoListRepository)
    private val addToDoListUseCase = AddToDoItemUseCase(toDoListRepository)


    val toDoListLiveData = getToDoListUseCase.getToDoList()


    fun deleteToDoItem(item: ToDoItem) {
        viewModelScope.launch {
            deleteToDoItemUseCase.deleteToDoItem(item)
        }
    }

    fun changeEnabled(item: ToDoItem) {
        viewModelScope.launch {
            editToDoListUseCase.editToDoItem(
                item.copy(enabled = !item.enabled)
            )
        }
    }

    fun add(item: ToDoItem) {
        viewModelScope.launch {
            addToDoListUseCase.addToDoItem(item)
        }
    }
}