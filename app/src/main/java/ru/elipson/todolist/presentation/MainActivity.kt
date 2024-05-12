package ru.elipson.todolist.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem
import java.util.Date

class MainActivity : AppCompatActivity(), ToDoItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: ToDoListViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter
    private var toDoItemLandscapeContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("view lifecycle", "MainActivity: {onCreate}")

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
        CoroutineScope(Dispatchers.IO).launch {
            val cursor = contentResolver.query(
                Uri.parse("content://ru.elipson.todolist/to_do_list"),
                null, null, null, null
            )

            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))

                val toDoItem = ToDoItem(name, description, enabled, Date(), id)

                Log.d("MainActivity", toDoItem.toString())
            }

            cursor?.close()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("view lifecycle", "MainActivity: {onStart}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("view lifecycle", "MainActivity: {onResume}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("view lifecycle", "MainActivity: {onPause}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("view lifecycle", "MainActivity: {onStop}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("view lifecycle", "MainActivity: {onDestroy}")
    }

    private fun openToDoItemLandscapeFragment(item: ToDoItem? = null) {
        val fragment = if (item == null) {
            ToDoItemFragment.instanceAddItem()
        } else {
            ToDoItemFragment.instanceChangeItem(item.id)
        }
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.toDoItemLandscapeContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
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

    override fun onEditingFinished() {
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }
}