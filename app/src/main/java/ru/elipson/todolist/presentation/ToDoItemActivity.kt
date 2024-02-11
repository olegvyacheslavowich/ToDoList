package ru.elipson.todolist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.elipson.todolist.R

class ToDoItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ToDoItemViewModel
    private lateinit var nameTextInputLayout: TextInputLayout
    private lateinit var nameEditText: TextInputEditText
    private lateinit var descriptionTextInputLayout: TextInputLayout
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var saveFab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item)

        initViews()

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        Log.d("ToDoItemActivity", mode.toString())

        viewModel = ViewModelProvider(this)[ToDoItemViewModel::class.java]

        viewModel.errorInputNameLiveData.observe(this) {

        }

        viewModel.errorInputDescriptionLiveData.observe(this) {

        }

        viewModel.toDoItemLiveData.observe(this) {

        }
    }

    private fun initViews() {
        nameTextInputLayout = findViewById(R.id.nameTextInputLayout)
        nameEditText = findViewById(R.id.nameEditText)
        descriptionTextInputLayout = findViewById(R.id.descriptionTextInputLayout)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        saveFab = findViewById(R.id.saveFab)
    }

    companion object {
        private const val EXTRA_TO_DO_ITEM_ID = "extra_to_do_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SCREEN_MODE_EDIT = "mode_edit"
        private const val EXTRA_SCREEN_MODE_ADD = "mode_add"

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