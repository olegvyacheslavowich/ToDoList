package ru.elipson.todolist.interview.decorator

abstract class Beverage {
    open var description: String = ""


    open fun cost(): Int {
        return 0
    }
}


class HouseBlend(): Beverage() {
    override fun cost(): Int {
        return 4
    }
}

open class CondimentDecorator(): Beverage() {

}

class Milk(private val component: Beverage): CondimentDecorator() {

    override fun cost(): Int {
        return component.cost() + 2
    }
}

class Soy(private val component: Beverage): CondimentDecorator() {

    override fun cost(): Int {
        return component.cost() + 10
    }
}

fun main() {

    val houseBlend = HouseBlend()

    val readyCoffee = Soy(Milk(houseBlend))
    println(readyCoffee.cost())


}
