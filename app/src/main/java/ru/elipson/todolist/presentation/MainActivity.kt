package ru.elipson.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import ru.elipson.todolist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]
        viewModel.toDoListLiveData.observe(this) { list ->
            Log.d("MainActivity", list.toString())
            viewModel.deleteToDoItem(list.first())
        }

    }
}