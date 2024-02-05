package ru.elipson.todolist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem
import java.lang.RuntimeException

class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>() {

    var list = listOf<ToDoItem>()
        set(value) {
            field = value
            // notifyDataSetChanged()
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