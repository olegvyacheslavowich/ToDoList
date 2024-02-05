package ru.elipson.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var adapter: ToDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]
        viewModel.toDoListLiveData.observe(this) { list ->
            adapter.list = list
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.toDoListRecyclerView)
        adapter = ToDoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            R.layout.item_todo_enabled,
            ToDoListAdapter.MAX_POOL_SIZE
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            R.layout.item_todo_disabled,
            ToDoListAdapter.MAX_POOL_SIZE
        )
    }
}