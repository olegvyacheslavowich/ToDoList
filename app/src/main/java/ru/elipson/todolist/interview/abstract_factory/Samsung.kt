package ru.elipson.todolist.interview.abstract_factory

import ru.elipson.todolist.interview.abstract_factory.memory.Memory
import ru.elipson.todolist.interview.abstract_factory.mobile_factory.MobileFactory
import ru.elipson.todolist.interview.abstract_factory.os.Os

data class Samsung(val mobileFactory: MobileFactory) : MobilePhone() {
    override val os: Os get() = mobileFactory.installOs()
    override val memory: Memory = mobileFactory.buildMemory()

    override fun build() {
        println("SAMSUNG has built with memory=${memory.javaClass.simpleName} && os=${os.javaClass.simpleName}")
    }

}
