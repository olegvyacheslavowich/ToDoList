package ru.elipson.todolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.elipson.todolist.domain.ToDoItem
import ru.elipson.todolist.domain.ToDoListRepository
import java.util.Date
import java.util.Random

object ToDoListRepositoryImpl : ToDoListRepository {

    private val list: MutableList<ToDoItem> = mutableListOf()
    private var autoIncrementId = 0
    private val listLiveData = MutableLiveData<List<ToDoItem>>()

    init {
        add(ToDoItem("Clean a house", "Clean a house", true, Date(), 0))
        add(ToDoItem("Buy products", "Buy products", false, Date(), 1))
        add(ToDoItem("Book a table in a restaurant","Book a table in a restaurant",  true,    Date(),  2 ) )
        add(ToDoItem("Wash a car", "Wash a car", false, Date(), 3))
        add(ToDoItem("Sleep...", "Zzz", true, Date(), 4))

        for (i in 5..1000) {
            add(
                ToDoItem(
                    id = i,
                    name = "Test",
                    description = "DEscription",
                    enabled = Random().nextBoolean(),
                    day = Date()
                )
            )
        }
    }

    override fun getListLiveData(): LiveData<List<ToDoItem>> = listLiveData

    override fun getList(): List<ToDoItem> = list.toList()
    override fun get(id: Int): ToDoItem? = list.find { it.id == id }

    override fun delete(item: ToDoItem) {
        list.remove(item)
        updateList()
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
        updateList()
    }

    private fun updateList() {
        listLiveData.value = list.toList().sortedBy { it.id }
    }
}