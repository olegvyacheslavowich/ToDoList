package ru.elipson.todolist.interview.factory_method

data class Samsung(val model: String): MobilePhone {
    override fun build() {
        println("SAMSUNG $model has built!")
    }

}
