package ru.elipson.todolist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class ToDoItemActivity : AppCompatActivity() {


    private var screenMode = MODE_UNKNOWN
    private var toDoItemId = ToDoItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("debug_log", "ToDoItemActivity: onCreate")
        setContentView(R.layout.activity_to_do_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != EXTRA_SCREEN_MODE_ADD && mode != EXTRA_SCREEN_MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == EXTRA_SCREEN_MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_TO_DO_ITEM_ID)) {
                throw RuntimeException("Param show item id is absent")
            }
            toDoItemId = intent.getIntExtra(EXTRA_TO_DO_ITEM_ID, ToDoItem.UNDEFINED_ID)
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            EXTRA_SCREEN_MODE_EDIT -> {
                ToDoItemFragment.instanceChangeItem(toDoItemId)
            }

            EXTRA_SCREEN_MODE_ADD -> {
                ToDoItemFragment.instanceAddItem()
            }

            else -> {
                throw RuntimeException("Unknown mode $screenMode")
            }
        }

        // supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.toDoItemContainer, fragment)
            .commit()
    }

    companion object {
        private const val EXTRA_TO_DO_ITEM_ID = "extra_to_do_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SCREEN_MODE_EDIT = "mode_edit"
        private const val EXTRA_SCREEN_MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context) =
            Intent(context, ToDoItemActivity::class.java)
                .putExtra(
                    EXTRA_SCREEN_MODE,
                    EXTRA_SCREEN_MODE_ADD
                )

        fun newIntentEditItem(context: Context, id: Int) =
            Intent(context, ToDoItemActivity::class.java)
                .putExtra(EXTRA_SCREEN_MODE, EXTRA_SCREEN_MODE_EDIT)
                .putExtra(EXTRA_TO_DO_ITEM_ID, id)
    }
}