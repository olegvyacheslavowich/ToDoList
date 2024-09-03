package ru.elipson.todolist.interview.abstract_factory

import ru.elipson.todolist.interview.abstract_factory.memory.Memory
import ru.elipson.todolist.interview.abstract_factory.os.Os

abstract class MobilePhone {

    protected abstract val os: Os
    protected abstract val memory: Memory

    abstract fun build()
}