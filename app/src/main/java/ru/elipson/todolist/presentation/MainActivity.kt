package ru.elipson.todolist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter
    private var toDoItemLandscapeContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toDoItemLandscapeContainer = findViewById(R.id.toDoItemLandscapeContainer)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[ToDoListViewModel::class.java]
        viewModel.toDoListLiveData.observe(this) { list ->
            toDoListAdapter.submitList(list)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.addFab)
        buttonAddItem.setOnClickListener {
            openToDoItem()
        }
    }

    private fun openToDoItemLandscapeFragment(item: ToDoItem? = null) {
        val fragment = if (item == null) {
            ToDoItemFragment.instanceAddItem()
        } else {
            ToDoItemFragment.instanceChangeItem(item.id)
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.toDoItemLandscapeContainer, fragment)
            .commit()
    }

    private fun openToDoItemActivity(item: ToDoItem? = null) {
        if (item == null) {
            val intent = ToDoItemActivity.newIntentAddItem(this)
            startActivity(intent)
        } else {
            val intent = ToDoItemActivity.newIntentEditItem(this, item.id)
            startActivity(intent)
        }
    }

    private fun isPortraitOrientation(): Boolean = toDoItemLandscapeContainer == null
    private fun openToDoItem(item: ToDoItem? = null) {
        if (isPortraitOrientation()) {
            openToDoItemActivity(item)
        } else {
            openToDoItemLandscapeFragment(item)

        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.toDoListRecyclerView)
        toDoListAdapter = ToDoListAdapter { item ->
            viewModel.changeEnabled(item)
        }
        toDoListAdapter.onClickListener = {
            openToDoItem(it)
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
                val item = toDoListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteToDoItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}