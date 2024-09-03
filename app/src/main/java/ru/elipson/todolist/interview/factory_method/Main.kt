package ru.elipson.todolist.interview.factory_method

fun main() {
    val factory = MobilePhoneFactory()
    val mobilePhone = factory.buildPhone(true)
    mobilePhone.build()
}
