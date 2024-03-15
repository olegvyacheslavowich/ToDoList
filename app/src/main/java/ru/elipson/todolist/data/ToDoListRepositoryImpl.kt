package ru.elipson.todolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.elipson.todolist.domain.ToDoItem
import ru.elipson.todolist.domain.ToDoListRepository
import java.util.Date
import java.util.Random

class ToDoListRepositoryImpl(application: Application) : ToDoListRepository {

    private val dao = AppDatabase.getInstance(application).getToDoItemsDao()
    private val mapper = ShopListMapper()

    override fun getListLiveData(): LiveData<List<ToDoItem>> = dao.getToDoItemsList().map {
        mapper.mapListDbModelToEntity(it)
    }

    override fun get(id: Int): ToDoItem {
        val dbModel = dao.getToDoItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun delete(item: ToDoItem) {
        dao.deleteToDoItem(item.id)
    }

    override fun edit(item: ToDoItem) {
        dao.addToDoItem(mapper.mapEntityToDbModel(item))
    }

    override fun add(item: ToDoItem) {
        dao.addToDoItem(mapper.mapEntityToDbModel(item))
    }

}