package ru.elipson.todolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.elipson.todolist.data.ToDoListRepositoryImpl
import ru.elipson.todolist.domain.AddToDoItemUseCase
import ru.elipson.todolist.domain.EditToDoItemUseCase
import ru.elipson.todolist.domain.GetToDoItemUseCase
import ru.elipson.todolist.domain.ToDoItem
import java.util.Date

class ToDoItemViewModel : ViewModel() {

    private val repository = ToDoListRepositoryImpl

    private val addToDoItemUseCase = AddToDoItemUseCase(repository)
    private val getToDoItemUseCase = GetToDoItemUseCase(repository)
    private val editToDoItemUseCase = EditToDoItemUseCase(repository)

    private val _shouldCloseScreenLiveData = MutableLiveData<Unit>()
    val shouldCloseScreenLiveData: LiveData<Unit> get() = _shouldCloseScreenLiveData

    private val _toDoItemLiveData = MutableLiveData<ToDoItem>()
    val toDoItemLiveData: LiveData<ToDoItem> get() = _toDoItemLiveData

    private val _errorInputNameLiveData = MutableLiveData<Boolean>()
    val errorInputNameLiveData: LiveData<Boolean> get() = _errorInputNameLiveData

    private val _errorInputDescriptionLiveData = MutableLiveData<Boolean>()
    val errorInputDescriptionLiveData: LiveData<Boolean> get() = _errorInputDescriptionLiveData
    fun getToDoItem(id: Int) {
        val item = getToDoItemUseCase.getToDoItem(id)
        _toDoItemLiveData.value = item
    }

    fun addToDoItem(inputName: String?, inputDescription: String?) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)

        val fieldsValid = validateInputName(name) && validateInputDescription(description)
        if (fieldsValid) {
            val item = ToDoItem(
                name = name,
                description = description,
                enabled = false,
                day = Date()
            )
            addToDoItemUseCase.addToDoItem(item)
            finishWork()
        }
    }

    fun editToDoItem(inputName: String?, inputDescription: String?) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)

        val fieldsValid = validateInputName(name) && validateInputDescription(description)
        if (fieldsValid) {
            _toDoItemLiveData.value?.let {
                val item = it.copy(
                    name = name,
                    description = description,
                    enabled = false,
                    day = Date()
                )
                editToDoItemUseCase.editToDoItem(item)
                finishWork()
            }
        }
    }

    private fun parseString(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun validateInputName(string: String): Boolean {
        val isBlank = string.isBlank()
        _errorInputNameLiveData.value = isBlank
        return !isBlank
    }

    private fun validateInputDescription(string: String): Boolean {
        val isBlank = string.isBlank()
        _errorInputDescriptionLiveData.value = isBlank
        return !isBlank
    }

    fun resetErrorInputNameLiveData() {
        _errorInputNameLiveData.value = false
    }

    fun resetErrorInputDescriptionLiveData() {
        _errorInputDescriptionLiveData.value = false
    }

    fun finishWork() {
        _shouldCloseScreenLiveData.value = Unit
    }

}