package ru.elipson.todolist.interview.abstract_factory.mobile_factory

import ru.elipson.todolist.interview.abstract_factory.memory.Memory
import ru.elipson.todolist.interview.abstract_factory.memory.Memory4GB
import ru.elipson.todolist.interview.abstract_factory.os.Android
import ru.elipson.todolist.interview.abstract_factory.os.Os

class KoreanMobileFactory : MobileFactory {
    override fun buildMemory(): Memory = Memory4GB()

    override fun installOs(): Os = Android()
}