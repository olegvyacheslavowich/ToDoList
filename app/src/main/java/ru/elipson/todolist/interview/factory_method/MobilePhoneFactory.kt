package ru.elipson.todolist.interview.factory_method

class MobilePhoneFactory() {

    fun buildPhone(isAndroid: Boolean): MobilePhone =
        if (isAndroid) {
            Samsung("Galaxy S4")
        } else {
            Iphone("5S")
        }
}