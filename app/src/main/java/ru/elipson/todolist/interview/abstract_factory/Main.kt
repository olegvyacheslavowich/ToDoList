package ru.elipson.todolist.interview.abstract_factory

fun main() {
    val factory = MobilePhoneShop()
    val mobilePhone = factory.buildPhone(false)
    mobilePhone.build()
}
