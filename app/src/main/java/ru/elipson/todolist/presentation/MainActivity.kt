package ru.elipson.todolist.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.elipson.todolist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]
        viewModel.toDoListLiveData.observe(this) { list ->
            toDoListAdapter.list = list
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.addFab)
        buttonAddItem.setOnClickListener {
            val intent = ToDoItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.toDoListRecyclerView)
        toDoListAdapter = ToDoListAdapter { item ->
            viewModel.changeEnabled(item)
        }
        toDoListAdapter.onClickListener = {
            val intent = ToDoItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }

        recyclerView.adapter = toDoListAdapter

        setMaxRecyclerViews(recyclerView)
        setupSwipeListener(recyclerView)
    }

    private fun setMaxRecyclerViews(recyclerView: RecyclerView) {
        recyclerView.recycledViewPool.setMaxRecycledViews(
            R.layout.item_todo_enabled,
            ToDoListAdapter.MAX_POOL_SIZE
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            R.layout.item_todo_disabled,
            ToDoListAdapter.MAX_POOL_SIZE
        )
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = toDoListAdapter.list[viewHolder.adapterPosition]
                viewModel.deleteToDoItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}