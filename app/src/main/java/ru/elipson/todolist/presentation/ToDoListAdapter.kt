package ru.elipson.todolist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.elipson.todolist.R
import ru.elipson.todolist.databinding.ItemTodoDisabledBinding
import ru.elipson.todolist.databinding.ItemTodoEnabledBinding
import ru.elipson.todolist.domain.ToDoItem

class ToDoListAdapter(private val onLongClickListener: ((ToDoItem) -> Unit)?) :
    ListAdapter<ToDoItem, ToDoListViewHolder>(ToDoListDiffItemCallback()) {

    var onClickListener: ((ToDoItem) -> Unit)? = null
    var onSwipeListener: ((ToDoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return ToDoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        val status = if (item.enabled) "active" else "passive"
        when (binding) {
            is ItemTodoEnabledBinding -> {
                binding.nameTextView.text = "${item.name}-$status"
                binding.descriptionTextView.text = item.description
                binding.dateTextView.text = item.day.time.toString()
            }

            is ItemTodoDisabledBinding -> {
                binding.nameTextView.text = "${item.name}-$status"
                binding.descriptionTextView.text = item.description
                binding.dateTextView.text = item.day.time.toString()
            }
        }

        binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }

        binding.root.setOnLongClickListener {
            onLongClickListener?.invoke(item)
            true
        }

    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).enabled) {
            R.layout.item_todo_enabled
        } else {
            R.layout.item_todo_disabled
        }

    companion object {
        const val MAX_POOL_SIZE = 15
    }

}