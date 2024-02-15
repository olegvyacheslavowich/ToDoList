package ru.elipson.todolist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class ToDoItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoItemViewModel
    private lateinit var nameTextInputLayout: TextInputLayout
    private lateinit var nameEditText: TextInputEditText
    private lateinit var descriptionTextInputLayout: TextInputLayout
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var saveFab: FloatingActionButton

    private var screenMode = MODE_UNKNOWN
    private var toDoItemId = ToDoItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item)


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