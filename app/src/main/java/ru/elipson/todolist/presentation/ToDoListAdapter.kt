package ru.elipson.todolist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class ToDoListAdapter(private val onLongClickListener: ((ToDoItem) -> Unit)?) :
    RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>() {

    var onClickListener: ((ToDoItem) -> Unit)? = null
    var onSwipeListener: ((ToDoItem) -> Unit)? = null

    var list = listOf<ToDoItem>()
        set(value) {
            val callback = ToDoListDiffCallback(list, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {

        return ToDoListViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val item = list[position]
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
        if (list[position].enabled) {
            R.layout.item_todo_enabled
        } else {
            R.layout.item_todo_disabled
        }

    override fun getItemCount(): Int = list.count()

    class ToDoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nameTextView: TextView
        var descriptionTextView: TextView
        var dateTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.nameTextView)
            descriptionTextView = view.findViewById(R.id.descriptionTextView)
            dateTextView = view.findViewById(R.id.dateTextView)
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }

}