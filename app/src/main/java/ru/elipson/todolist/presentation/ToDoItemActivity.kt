package ru.elipson.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ru.elipson.todolist.R

class ToDoItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item)

        viewModel = ViewModelProvider(this)[ToDoItemViewModel::class.java]

        viewModel.errorInputNameLiveData.observe(this) {

        }

        viewModel.errorInputDescriptionLiveData.observe(this) {

        }

        viewModel.toDoItemLiveData.observe(this) {

        }
    }
}