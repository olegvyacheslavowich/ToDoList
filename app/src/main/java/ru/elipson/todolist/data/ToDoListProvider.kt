package ru.elipson.todolist.data

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ToDoListProvider : ContentProvider() {

    private lateinit var db: AppDatabase
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("ru.elipson.todolist", TO_DO_LIST_TABLE, TO_DO_LIST_TABLE_CODE)
        addURI("ru.elipson.todolist", TO_DO_LIST_TABLE_BY_ID, TO_DO_LIST_TABLE_BY_ID_CODE)
    }

    override fun onCreate(): Boolean {
        db = AppDatabase.getInstance(context as Application)
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code = uriMatcher.match(uri)
        when (code) {
            TO_DO_LIST_TABLE_CODE -> {
                return db.getToDoItemsDao().getToDoItemsListCursor()
            }

            TO_DO_LIST_TABLE_BY_ID_CODE -> {
                Log.d("ToDoListProvider", "matched")
                Log.d("ToDoListProvider", "query $uri")
                return null
            }

            else -> {
                Log.d("ToDoListProvider", "query $uri")
                return null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    companion object {
        private const val TO_DO_LIST_TABLE = "to_do_list"
        private const val TO_DO_LIST_TABLE_BY_ID = "to_do_list/#"
        private const val TO_DO_LIST_TABLE_CODE = 100
        private const val TO_DO_LIST_TABLE_BY_ID_CODE = 101
    }

}