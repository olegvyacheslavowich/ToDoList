package ru.elipson.todolist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.elipson.todolist.R

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