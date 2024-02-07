package ru.elipson.todolist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class ToDoListAdapter(private val onLongClickListener: ((ToDoItem) -> Unit)?) :
    ListAdapter<ToDoItem, ToDoListViewHolder>(ToDoListDiffItemCallback()) {

    var onClickListener: ((ToDoItem) -> Unit)? = null
    var onSwipeListener: ((ToDoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {

        return ToDoListViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val item = getItem(position)
        val status = if (item.enabled) "active" else "passive"

        holder.nameTextView.text = "${item.name}-$status"
        holder.descriptionTextView.text = item.description
        holder.dateTextView.text = item.day.time.toString()

        holder.itemView.setOnClickListener {
            onClickListener?.invoke(item)
        }

        holder.itemView.setOnLongClickListener {
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