package ru.elipson.todolist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.elipson.todolist.R
import ru.elipson.todolist.domain.ToDoItem

class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>() {

    private val list = mutableListOf<ToDoItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        Log.i("ToDoListAdapter", "inflate")

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo_enabled, parent, false)
        return ToDoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {

        Log.i("ToDoListAdapter", "onBind")

        holder.nameTextView.text = list[position].name
        holder.descriptionTextView.text = list[position].description
        holder.dateTextView.text = list[position].day.time.toString()
    }

    override fun getItemCount(): Int = list.count()

    fun updateList(newList: List<ToDoItem>) {
        list.clear()
        list.addAll(newList)
    }

    class ToDoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nameTextView: TextView
        var descriptionTextView: TextView
        var dateTextView: TextView

        init {
            Log.i("ToDoListAdapter", "findViewById")
            nameTextView = view.findViewById<TextView>(R.id.nameTextView)
            descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
            dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        }


    }

}