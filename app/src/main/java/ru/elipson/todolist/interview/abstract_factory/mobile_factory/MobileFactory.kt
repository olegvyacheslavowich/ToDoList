package ru.elipson.todolist.interview.abstract_factory.mobile_factory

import ru.elipson.todolist.interview.abstract_factory.memory.Memory
import ru.elipson.todolist.interview.abstract_factory.os.Os

interface MobileFactory {

    fun buildMemory(): Memory
    fun installOs(): Os
}