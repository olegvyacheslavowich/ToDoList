package ru.elipson.todolist.data

import ru.elipson.todolist.domain.ToDoItem
import ru.elipson.todolist.domain.ToDoListRepository
import java.util.Date

object ToDoListRepositoryImpl : ToDoListRepository {

    private val list: MutableList<ToDoItem> = mutableListOf()
    private var autoIncrementId = 0

    init {
        add(ToDoItem("Clean a house", "Clean a house", true, Date()))
        add(ToDoItem("Buy products", "Buy products", false, Date()))
        add(ToDoItem("Book a table in a restaurant", "Book a table in a restaurant", true, Date()))
        add(ToDoItem("Wash a car", "Wash a car", false, Date()))
        add(ToDoItem("Sleep...", "Zzz", true, Date()))
    }

    override fun getList(): List<ToDoItem> = list.toList()
    override fun get(id: Int): ToDoItem? = list.find { it.id == id }

    override fun delete(item: ToDoItem) {
        list.remove(item)
    }

    override fun edit(item: ToDoItem) {
        list.remove(get(item.id))
        add(item)
    }

    override fun add(item: ToDoItem) {
        if (item.id == ToDoItem.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        list.add(item)
    }
}