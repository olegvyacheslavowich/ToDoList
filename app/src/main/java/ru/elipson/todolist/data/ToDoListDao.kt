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
    fun getToDoItemsList(): LiveData<List<ToDoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDoItem(toDoItemDbModel: ToDoItemDbModel)

    @Query("DELETE FROM to_do_item WHERE id = :toDoItemDbModelId")
    suspend fun deleteToDoItem(toDoItemDbModelId: Int)

    @Query("SELECT * FROM to_do_item WHERE id = :toDoItemDbModelId LIMIT 1")
    suspend fun getToDoItem(toDoItemDbModelId: Int): ToDoItemDbModel

}