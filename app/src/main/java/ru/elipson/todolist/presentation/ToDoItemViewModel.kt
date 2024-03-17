package ru.elipson.todolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.elipson.todolist.data.ToDoListRepositoryImpl
import ru.elipson.todolist.domain.AddToDoItemUseCase
import ru.elipson.todolist.domain.EditToDoItemUseCase
import ru.elipson.todolist.domain.GetToDoItemUseCase
import ru.elipson.todolist.domain.ToDoItem
import java.util.Date

class ToDoItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)

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
        viewModelScope.launch {
            getToDoItemUseCase.getToDoItem(id)?.let {
                _toDoItemLiveData.value = it
            }
        }
    }

    fun addToDoItem(inputName: String?, inputDescription: String?) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)

        val nameIsValid = validateInputName(name)
        val descriptionIsValid = validateInputDescription(description)

        val fieldsValid = nameIsValid && descriptionIsValid
        if (fieldsValid) {
            val item = ToDoItem(
                name = name,
                description = description,
                enabled = false,
                day = Date()
            )
            viewModelScope.launch {
                addToDoItemUseCase.addToDoItem(item)
                finishWork()
            }
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
                viewModelScope.launch {
                    editToDoItemUseCase.editToDoItem(item)
                    finishWork()
                }
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

    private fun finishWork() {
        _shouldCloseScreenLiveData.postValue(Unit)
    }
}