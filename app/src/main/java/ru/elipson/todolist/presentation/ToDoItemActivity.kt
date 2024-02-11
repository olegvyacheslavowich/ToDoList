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
        viewModel = ViewModelProvider(this)[ToDoItemViewModel::class.java]

        parseIntent()
        initViews()
        setMode()
        subscribeOnViewModel()
        addTextChangeListeners()
    }

    private fun addTextChangeListeners() {
        nameEditText.addTextChangedListener {
            viewModel.resetErrorInputNameLiveData()
        }

        descriptionEditText.addTextChangedListener {
            viewModel.resetErrorInputDescriptionLiveData()
        }
    }

    private fun subscribeOnViewModel() {
        viewModel.errorInputNameLiveData.observe(this) {
            when (it) {
                null, false -> {
                    nameTextInputLayout.error = null
                }

                true -> {
                    nameTextInputLayout.error = getString(R.string.name_incorrect_error_message)
                }
            }
        }

        viewModel.errorInputDescriptionLiveData.observe(this) {
            when (it) {
                null, false -> {
                    descriptionTextInputLayout.error = null
                }

                true -> {
                    descriptionTextInputLayout.error =
                        getString(R.string.description_incorrect_error_message)
                }
            }
        }

        viewModel.shouldCloseScreenLiveData.observe(this) {
            finish()
        }
    }

    private fun setMode() {
        when (screenMode) {
            EXTRA_SCREEN_MODE_EDIT -> {
                launchEditMode()
            }

            EXTRA_SCREEN_MODE_ADD -> {
                launchAddMode()
            }
        }
    }

    private fun launchEditMode() {
        viewModel.getToDoItem(toDoItemId)

        viewModel.toDoItemLiveData.observe(this) { item ->
            item?.let {
                nameEditText.setText(it.name)
                descriptionEditText.setText(it.description)
            }
        }

        saveFab.setOnClickListener {
            viewModel.editToDoItem(
                inputName = nameEditText.text.toString(),
                inputDescription = descriptionEditText.text.toString()
            )
        }
    }

    private fun launchAddMode() {
        saveFab.setOnClickListener {
            viewModel.addToDoItem(
                inputName = nameEditText.text.toString(),
                inputDescription = descriptionEditText.text.toString()
            )
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