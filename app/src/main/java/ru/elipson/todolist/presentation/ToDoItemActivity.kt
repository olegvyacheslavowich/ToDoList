package ru.elipson.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.elipson.todolist.R

class ToDoItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item)
    }
}