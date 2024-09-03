package ru.elipson.todolist.interview.abstract_factory

import ru.elipson.todolist.interview.abstract_factory.memory.Memory
import ru.elipson.todolist.interview.abstract_factory.mobile_factory.MobileFactory
import ru.elipson.todolist.interview.abstract_factory.os.Os

data class Iphone(val mobileFactory: MobileFactory) : MobilePhone() {
    override lateinit var os: Os
    override val memory: Memory = mobileFactory.buildMemory()

    override fun build() {
        os = mobileFactory.installOs()
        println("IPHONE has built with memory=${memory.javaClass.simpleName} && os=${os.javaClass.simpleName}")
    }
}
