package ru.elipson.todolist.interview.abstract_factory

import ru.elipson.todolist.interview.abstract_factory.mobile_factory.KoreanMobileFactory
import ru.elipson.todolist.interview.abstract_factory.mobile_factory.USAMobileFactory

class MobilePhoneShop() {

    fun buildPhone(isAndroid: Boolean): MobilePhone =
        if (isAndroid) {
            Samsung(KoreanMobileFactory())
        } else {
            Iphone(USAMobileFactory())
        }
}