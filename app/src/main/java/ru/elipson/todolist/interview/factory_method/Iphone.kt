package ru.elipson.todolist.interview.factory_method

data class Iphone(val model: String): MobilePhone {
    override fun build() {
        println("IPHONE $model has built!")

    }
}
