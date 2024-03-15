package ru.elipson.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.elipson.todolist.domain.ToDoItem

@Dao
interface ToDoListDao {


    @Query("SELECT * FROM to_do_item")
    fun getToDoItemsList(): LiveData<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(toDoItemDbModel: ToDoItemDbModel)

    @Query("DELETE FROM to_do_item WHERE id = :toDoItemDbModelId")
    fun deleteToDoItem(toDoItemDbModelId: Int)

    @Query("SELECT * FROM to_do_item WHERE id = :toDoItemDbModelId LIMIT 1")
    fun getToDoItem(toDoItemDbModelId: Int): LiveData<List<ToDoItem>>

}