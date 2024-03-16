package ru.elipson.todolist.data

import ru.elipson.todolist.domain.ToDoItem
import java.util.Date

class ShopListMapper {

    fun mapEntityToDbModel(entity: ToDoItem): ToDoItemDbModel =
        ToDoItemDbModel(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            enabled = entity.enabled,
            day = entity.day.time
        )

    fun mapDbModelToEntity(dbModel: ToDoItemDbModel): ToDoItem =
        ToDoItem(
            id = dbModel.id,
            name = dbModel.name,
            description = dbModel.description,
            enabled = dbModel.enabled,
            day = Date(dbModel.day)
        )

    fun mapListEntityToDbModel(entityList: List<ToDoItem>): List<ToDoItemDbModel> =
        entityList.map {
            ToDoItemDbModel(
                id = it.id,
                name = it.name,
                description = it.description,
                enabled = it.enabled,
                day = it.day.time
            )
        }

    fun mapListDbModelToEntity(dbModelList: List<ToDoItemDbModel>): List<ToDoItem> =
        dbModelList.map {
            ToDoItem(
                id = it.id,
                name = it.name,
                description = it.description,
                enabled = it.enabled,
                day = Date(it.day)
            )
        }


}