package ru.elipson.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var listLinearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listLinearLayout = findViewById(R.id.toDoListLinearLayout)

        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]
        viewModel.toDoListLiveData.observe(this) { list ->
            showList(list)
        }

        findViewById<FloatingActionButton>(R.id.addFab).setOnClickListener {
            viewModel.add(
                ToDoItem(
                    name = "Test",
                    description = "DEscription",
                    enabled = false,
                    day = Date()
                )
            )
        }
    }

    private fun showList(list: List<ToDoItem>) {

        listLinearLayout.removeAllViews()
        for (i in 0..100) {
            list.forEach { item ->
                val layoutId = if (item.enabled) {
                    R.layout.item_todo_enabled
                } else {
                    R.layout.item_todo_disabled
                }

                val view =
                    LayoutInflater.from(applicationContext)
                        .inflate(layoutId, listLinearLayout, false)

                val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
                val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
                val dateTextView = view.findViewById<TextView>(R.id.dateTextView)

                nameTextView.text = item.name
                descriptionTextView.text = item.description
                dateTextView.text = item.day.time.toString()

                listLinearLayout.addView(view)
            }
        }
    }
}