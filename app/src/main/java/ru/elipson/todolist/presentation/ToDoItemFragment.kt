package ru.elipson.todolist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class ToDoItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val toDoItemId: Int = ToDoItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ToDoItemViewModel
    private lateinit var nameTextInputLayout: TextInputLayout
    private lateinit var nameEditText: TextInputEditText
    private lateinit var descriptionTextInputLayout: TextInputLayout
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var saveFab: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_to_do_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ToDoItemViewModel::class.java]
        parseParams()
        initViews(view)
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
        viewModel.errorInputNameLiveData.observe(viewLifecycleOwner) {
            when (it) {
                null, false -> {
                    nameTextInputLayout.error = null
                }

                true -> {
                    nameTextInputLayout.error = getString(R.string.name_incorrect_error_message)
                }
            }
        }

        viewModel.errorInputDescriptionLiveData.observe(viewLifecycleOwner) {
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

        viewModel.shouldCloseScreenLiveData.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
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

        viewModel.toDoItemLiveData.observe(viewLifecycleOwner) { item ->
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

    private fun parseParams() {
        if (screenMode != EXTRA_SCREEN_MODE_EDIT && screenMode != EXTRA_SCREEN_MODE_ADD) {
            throw RuntimeException("Param screen mode is absent")
        }

        if (screenMode == EXTRA_SCREEN_MODE_EDIT && toDoItemId == ToDoItem.UNDEFINED_ID) {
            throw RuntimeException("Param show item id is absent")
        }
    }

    private fun initViews(view: View) {
        nameTextInputLayout = view.findViewById(R.id.nameTextInputLayout)
        nameEditText = view.findViewById(R.id.nameEditText)
        descriptionTextInputLayout = view.findViewById(R.id.descriptionTextInputLayout)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        saveFab = view.findViewById(R.id.saveFab)
    }

    companion object {

        private const val EXTRA_TO_DO_ITEM_ID = "extra_to_do_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SCREEN_MODE_EDIT = "mode_edit"
        private const val EXTRA_SCREEN_MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun instanceAddItem(): ToDoItemFragment = ToDoItemFragment(EXTRA_SCREEN_MODE_ADD)

        fun instanceChangeItem(id: Int): ToDoItemFragment =
            ToDoItemFragment(EXTRA_SCREEN_MODE_EDIT, id)

    }


}