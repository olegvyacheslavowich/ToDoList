package ru.elipson.todolist.interview.abstract_factory.mobile_factory

import ru.elipson.todolist.interview.abstract_factory.memory.Memory
import ru.elipson.todolist.interview.abstract_factory.memory.Memory2GB
import ru.elipson.todolist.interview.abstract_factory.os.Ios
import ru.elipson.todolist.interview.abstract_factory.os.Os

class USAMobileFactory: MobileFactory {
    override fun buildMemory(): Memory = Memory2GB()

    override fun installOs(): Os = Ios()
}